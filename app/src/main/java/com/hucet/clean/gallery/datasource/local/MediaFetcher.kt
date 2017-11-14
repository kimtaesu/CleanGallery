package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.hucet.clean.gallery.extension.getFilenameFromPath
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeHelper
import com.hucet.clean.gallery.model.Medium
import java.util.*

/**
 * Created by taesu on 2017-10-30.
 */

class MediaFetcher constructor(private val context: Context,
                               val filters: Set<MediaTypeFilter>
) {
    fun query(curPath: String, sortOption: String = MediaStore.Images.ImageColumns.DATE_MODIFIED + " DESC"): Cursor {
        return MediaProvider().query(context, curPath, sortOption)
    }

    fun isFilter(filters: Set<MediaTypeFilter>, medium: Medium, noMediaFolders: Set<String>): Boolean {
        val isFilter = filters.any {
            it.filterd(medium, noMediaFolders) == FILTERED
        }
        if (isFilter)
            return FILTERED
        return NOT_FILTERED
    }

    fun parseCursor(cur: Cursor?, noMediaFolders: Set<String>): List<Medium> {
        cur ?: return emptyList()
        val curMedia = ArrayList<Medium>()
        cur.use {
            if (cur.moveToFirst()) {
                do {
                    try {
                        val id = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                        val path = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).trim()
                        var filename = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))?.trim() ?: path.getFilenameFromPath()
                        var size = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                        val dateTaken = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN))
                        val dateModified = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED))
                        val medium = Medium(id, filename, path, dateModified, dateTaken, size, MediaTypeHelper.isVideo(filename))
                        if (isFilter(filters, medium, noMediaFolders) == MediaTypeFilter.FILTERED)
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
                MediaStore.Images.Media.SIZE)

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

}
