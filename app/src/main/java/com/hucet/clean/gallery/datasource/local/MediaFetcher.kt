package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.extension.*
import com.hucet.clean.gallery.model.Medium
import java.io.File

/**
 * Created by taesu on 2017-10-30.
 */

class MediaFetcher constructor(private val context: Context, private val applcationConfig: ApplicationConfig) {

    val sortOption = MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC"
    val imageProjection = arrayOf(MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.SIZE)

    fun queryImage(): Cursor {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        return context.contentResolver.query(uri, imageProjection, null, null, sortOption)
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
                        val dateModified = cur.getInt(cur.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)) * 1000L

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
}
