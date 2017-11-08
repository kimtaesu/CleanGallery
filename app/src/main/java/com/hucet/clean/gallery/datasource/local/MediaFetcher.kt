package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.extension.getFilenameFromPath
import com.hucet.clean.gallery.model.Medium
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by taesu on 2017-10-30.
 */

class MediaFetcher constructor(private val context: Context, private val applcationConfig: ApplicationConfig) {
    val format: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd")
    }

    enum class CategoryType {
        DIR, UPDATE_DATE
    }

    val sortOption = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
    val imageProjection = arrayOf(MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.SIZE)

    fun queryImage(curPath: String): Cursor {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = getSelectionExpression()
        val selectionArgs = getSelectionArgs(curPath)
        return context.contentResolver.query(uri, imageProjection, selection, selectionArgs, sortOption)
    }

    private fun getSelectionExpression(): String {
        return "${MediaStore.Images.Media.DATA} LIKE ?"
    }

    private fun getSelectionArgs(curPath: String): Array<out String> {
        return arrayOf("${curPath}/%")
    }

    fun parseCursor(cur: Cursor?): List<Medium> {
        cur ?: return emptyList()
        val curMedia = ArrayList<Medium>()
        cur.use {
            if (cur.moveToFirst()) {
                do {
                    try {
                        val path = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).trim()
                        var filename = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))?.trim() ?: path.getFilenameFromPath()
                        var size = cur.getLong(cur.getColumnIndex(MediaStore.Images.Media.SIZE))
                        val dateTaken = cur.getLong(cur.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN))
                        val dateModified = cur.getLong(cur.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)) * 1000L

                        val medium = Medium(filename, path, false, dateModified, dateTaken, size)

                        curMedia.add(medium)

                    } catch (e: IllegalArgumentException) {
                        continue
                    }
                } while (cur.moveToNext())
            }
        }
        return curMedia
    }

    fun category(categoryType: CategoryType, mediums: List<Medium>): Map<String, List<Medium>> {
        return when (categoryType) {
            CategoryType.DIR -> {
                mediums.groupBy { medium ->
                    File(medium.path)?.parentFile.name
                }
            }
            CategoryType.UPDATE_DATE -> {
                mediums.groupBy { medium ->
                    format.format(medium.modified)
                }
            }
        }
    }
}
