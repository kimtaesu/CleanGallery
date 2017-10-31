package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.extension.*
import com.hucet.clean.gallery.model.Medium
import java.io.File
import java.util.LinkedHashMap
import kotlin.collections.ArrayList
import kotlin.collections.component1
import kotlin.collections.component2

/**
 * Created by taesu on 2017-10-30.
 */

class MediaFetcher constructor(val context: Context, val applcationConfig: ApplicationConfig) {
    var shouldStop = false

    fun getMediaByDirectories(isPickVideo: Boolean, isPickImage: Boolean): HashMap<String, ArrayList<Medium>> {
        val media = getFilesFrom("", isPickImage, isPickVideo)
        val excludedPaths = applcationConfig.excludedFolders
        val includedPaths = applcationConfig.includedFolders
        val showHidden = applcationConfig.shouldShowHidden
        val directories = groupDirectories(media)

        val removePaths = ArrayList<String>()
        for ((_, curMedia) in directories) {
            // make sure the path has uppercase letters wherever appropriate
            val groupPath = File(curMedia.first().path).parent
            if (!File(groupPath).exists() || !shouldFolderBeVisible(groupPath, excludedPaths, includedPaths, showHidden)) {
                removePaths.add(groupPath.toLowerCase())
            }
        }

        removePaths.forEach {
            directories.remove(it)
        }

        return directories
    }

    fun getFilesFrom(curPath: String, isPickImage: Boolean, isPickVideo: Boolean): ArrayList<Medium> {
        val projection = arrayOf(MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE)
        val uri = MediaStore.Files.getContentUri("external")
        val selection = getSelectionQuery(curPath)
        val selectionArgs = getSelectionArgsQuery(curPath)

        return try {
            val cur = context.contentResolver.query(uri, projection, selection, selectionArgs, getSortingForFolder(curPath))
            parseCursor(cur, isPickImage, isPickVideo, curPath)
        } catch (e: Exception) {
            ArrayList()
        }
    }

    private fun getSelectionQuery(path: String): String? {
        val dataQuery = "${MediaStore.Images.Media.DATA} LIKE ?"
        return if (path.isEmpty()) {
            if (context.isAndroidFour())
                return null

            var query = "($dataQuery) OR ($dataQuery)"
            query
        } else {
            "($dataQuery AND ${MediaStore.Images.Media.DATA} NOT LIKE ?)"
        }
    }

    private fun getSelectionArgsQuery(path: String): Array<String>? {
        return if (path.isEmpty()) {
            if (context.isAndroidFour())
                return null
            arrayOf("${getExternalStorageDirectory()}/%", "${getSdCardStorageDirectory()}/%")
        } else {
            arrayOf("$path/%", "$path/%/%")
        }
    }

    private fun parseCursor(cur: Cursor, isPickImage: Boolean, isPickVideo: Boolean, curPath: String): ArrayList<Medium> {
        val curMedia = ArrayList<Medium>()
        val filterMedia = applcationConfig.filterMedia
        val showHidden = applcationConfig.shouldShowHidden
        val includedFolders = applcationConfig.includedFolders.map { "${it.trimEnd('/')}/" }
        val excludedFolders = applcationConfig.excludedFolders.map { "${it.trimEnd('/')}/" }
        val noMediaFolders = getNoMediaFolders()
        val isThirdPartyIntent = applcationConfig.isThirdPartyIntent

        cur.use {
            if (cur.moveToFirst()) {
                do {
                    try {
                        if (shouldStop)
                            break

                        val path = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).trim()
                        var filename = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))?.trim() ?: ""
                        if (filename.isEmpty())
                            filename = path.getFilenameFromPath()

                        val isImage = filename.isImageFast()
                        val isVideo = if (isImage) false else filename.isVideoFast()
                        val isGif = if (isImage || isVideo) false else filename.isGif()

                        if (!isImage && !isVideo && !isGif)
                            continue

                        if (isVideo && (isPickImage || filterMedia and VIDEOS == 0))
                            continue

                        if (isImage && (isPickVideo || filterMedia and IMAGES == 0))
                            continue

                        if (isGif && filterMedia and GIFS == 0)
                            continue

                        if (!showHidden && filename.startsWith('.'))
                            continue

                        var size = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))

                        val file = File(path)
                        if (size == 0L) {
                            size = file.length()
                        }

                        if (size <= 0L)
                            continue

                        var isExcluded = false
                        excludedFolders.forEach {
                            if (path.startsWith(it)) {
                                isExcluded = true
                                includedFolders.forEach {
                                    if (path.startsWith(it)) {
                                        isExcluded = false
                                    }
                                }
                            }
                        }

                        if (!isExcluded && !showHidden) {
                            noMediaFolders.forEach {
                                if (path.startsWith(it)) {
                                    isExcluded = true
                                }
                            }
                        }

                        if (!isExcluded && !showHidden && path.contains("/.")) {
                            isExcluded = true
                        }

                        if (!isExcluded || isThirdPartyIntent) {
                            if (!file.exists())
                                continue
                            val dateTaken = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN))
                            val dateModified = cur.getInt(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)) * 1000L

                            val medium = Medium(filename, path, isVideo, dateModified, dateTaken, size)
                            curMedia.add(medium)
                        }
                    } catch (e: Exception) {
                        continue
                    }
                } while (cur.moveToNext())
            }
        }

        applcationConfig.includedFolders.filter { it.isNotEmpty() && (curPath.isEmpty() || it == curPath) }.forEach {
            getMediaInFolder(it, curMedia, isPickImage, isPickVideo, filterMedia)
        }

        if (isThirdPartyIntent && curPath.isNotEmpty() && curMedia.isEmpty()) {
            getMediaInFolder(curPath, curMedia, isPickImage, isPickVideo, filterMedia)
        }

        Medium.sorting = applcationConfig.getFileSorting(curPath)
        curMedia.sort()

        return curMedia
    }

    private fun groupDirectories(media: ArrayList<Medium>): HashMap<String, ArrayList<Medium>> {
        val directories = LinkedHashMap<String, ArrayList<Medium>>()
        for (medium in media) {
            if (shouldStop)
                break

            val parentDir = File(medium.path).parent?.toLowerCase() ?: continue
            if (directories.containsKey(parentDir)) {
                directories[parentDir]!!.add(medium)
            } else {
                directories.put(parentDir, arrayListOf(medium))
            }
        }
        return directories
    }

    private fun shouldFolderBeVisible(path: String, excludedPaths: Set<String>, includedPaths: Set<String>, showHidden: Boolean): Boolean {
        val file = File(path)
        return if (includedPaths.contains(path)) {
            true
        } else if (isThisOrParentExcluded(path, excludedPaths, includedPaths)) {
            false
        } else if (!showHidden && file.isDirectory && file.canonicalFile == file.absoluteFile) {
            var containsNoMediaOrDot = file.containsNoMedia() || path.contains("/.")
            if (!containsNoMediaOrDot) {
                containsNoMediaOrDot = checkParentHasNoMedia(file.parentFile)
            }
            !containsNoMediaOrDot
        } else {
            true
        }
    }

    private fun checkParentHasNoMedia(file: File): Boolean {
        var curFile = file
        while (true) {
            if (curFile.containsNoMedia()) {
                return true
            }
            curFile = curFile.parentFile
            if (curFile.absolutePath == "/")
                break
        }
        return false
    }

    private fun isThisOrParentExcluded(path: String, excludedPaths: Set<String>, includedPaths: Set<String>) =
            includedPaths.none { path.startsWith(it) } && excludedPaths.any { path.startsWith(it) }


    private fun getMediaInFolder(folder: String, curMedia: ArrayList<Medium>, isPickImage: Boolean, isPickVideo: Boolean, filterMedia: Int) {
        val files = File(folder).listFiles() ?: return
        for (file in files) {
            if (shouldStop)
                break

            val filename = file.name
            val isImage = filename.isImageFast()
            val isVideo = if (isImage) false else filename.isVideoFast()
            val isGif = if (isImage || isVideo) false else filename.isGif()

            if (!isImage && !isVideo)
                continue

            if (isVideo && (isPickImage || filterMedia and VIDEOS == 0))
                continue

            if (isImage && (isPickVideo || filterMedia and IMAGES == 0))
                continue

            if (isGif && filterMedia and GIFS == 0)
                continue

            val size = file.length()
            if (size <= 0L)
                continue

            val dateTaken = file.lastModified()
            val dateModified = file.lastModified()

            val medium = Medium(filename, file.absolutePath, isVideo, dateModified, dateTaken, size)
            val isAlreadyAdded = curMedia.any { it.path == file.absolutePath }
            if (!isAlreadyAdded) {
                curMedia.add(medium)
                context.scanPath(file.absolutePath) {}
            }
        }
    }

    private fun getSortingForFolder(path: String): String {
        val sorting = applcationConfig.getFileSorting(path)
        val sortValue = when {
            sorting and SORT_BY_NAME > 0 -> MediaStore.Images.Media.DISPLAY_NAME
            sorting and SORT_BY_SIZE > 0 -> MediaStore.Images.Media.SIZE
            sorting and SORT_BY_DATE_MODIFIED > 0 -> MediaStore.Images.Media.DATE_MODIFIED
            else -> MediaStore.Images.Media.DATE_TAKEN
        }

        return if (sorting and SORT_DESCENDING > 0)
            "$sortValue DESC"
        else
            "$sortValue ASC"
    }

    private fun getNoMediaFolders(): ArrayList<String> {
        val folders = ArrayList<String>()
        val noMediaCondition = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_NONE}"

        val uri = MediaStore.Files.getContentUri("external")
        val columns = arrayOf(MediaStore.Files.FileColumns.DATA)
        val where = "$noMediaCondition AND ${MediaStore.Files.FileColumns.TITLE} LIKE ?"
        val args = arrayOf("%${NOMEDIA}%")
        var cursor: Cursor? = null

        try {
            cursor = context.contentResolver.query(uri, columns, where, args, null)
            if (cursor?.moveToFirst() == true) {
                do {
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)) ?: continue
                    val noMediaFile = File(path)
                    if (noMediaFile.exists())
                        folders.add("${noMediaFile.parent}/")
                } while (cursor.moveToNext())
            }
        } finally {
            cursor?.close()
        }

        return folders
    }
}
