package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.hucet.clean.gallery.extension.getFilenameFromPath
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeHelper
import com.hucet.clean.gallery.gallery.filter.OrchestraFilter
import com.hucet.clean.gallery.model.Medium
import java.util.*

/**
 * Created by taesu on 2017-10-30.
 */

open class MediaFetcher constructor(private val context: Context,
                                    private val orchestraFilter: OrchestraFilter
) {
    fun query(curPath: () -> String): Cursor =
            MediaProvider().query(context, curPath.invoke(), MediaStore.Images.ImageColumns.DATE_MODIFIED + " DESC")


    fun parseCursor(cur: Cursor?, noMediaFolders: Set<String>): List<Medium> {
        cur ?: return emptyList()
        val curMedia = ArrayList<Medium>()

        cur.use {
            if (cur.moveToFirst()) {
                do {
                    try {
                        val id = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                        val path = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).trim()
                        var fileName = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))?.trim() ?: path.getFilenameFromPath()
                        var size = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                        val dateTaken = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN))
                        val dateModified = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED))
                        val orientation = cur.getInt(cur.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION))
                        val mimeType = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)) ?: getMimetypeFromExtension(fileName)
                        val medium = Medium(id, fileName, path, dateModified, dateTaken, size, orientation, mimeType, MediaTypeHelper.mediaType(fileName))
                        if (orchestraFilter.filterd(medium, noMediaFolders) == FILTERED)
                            continue

                        curMedia.add(medium)

                    } catch (e: IllegalArgumentException) {
                        e.printStackTrace()
                        continue
                    }
                } while (cur.moveToNext())
            }
        }
        return curMedia
    }


    private class MediaProvider {
        private val projection = arrayOf(MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.MIME_TYPE
        )

        fun query(context: Context, curPath: String, sortOption: String): Cursor {
            val uri = MediaStore.Files.getContentUri("external")
            val selectionClause = getSelectionClause()
            val selectionArgs = getSelectionArgs(curPath)
            return context.contentResolver.query(uri, projection, selectionClause, selectionArgs, sortOption)
        }

        private fun getSelectionClause(): String {
            return "${MediaStore.Images.Media.DATA} LIKE ?"
        }

        private fun getSelectionArgs(curPath: String): Array<out String> {
            return arrayOf("${curPath}/%")
        }
    }

    open fun getMimetypeFromExtension(fileName: String): String = MimeTypeMap.getFileExtensionFromUrl(fileName)
}
