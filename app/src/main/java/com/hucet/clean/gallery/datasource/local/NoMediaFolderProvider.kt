package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import java.io.File

/**
 * Created by taesu on 2017-11-13.
 */
class NoMediaFolderProvider(private val context: Context) {
    private val projection = arrayOf(
            MediaStore.Images.Media.DATA
    )


    private val selectionClause: String
        get() {
            val noMediaType = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_NONE}"
            return "$noMediaType AND ${MediaStore.Files.FileColumns.TITLE} LIKE ?"
        }
    private val selectionArgs: Array<String>
        get() {
            return arrayOf("%.nomedia%")
        }

    open fun query(): Cursor? {
        val uri = MediaStore.Files.getContentUri("external")
        return context.contentResolver.query(uri, projection, selectionClause, selectionArgs, null)
    }

    fun parseCursor(cur: Cursor?,
                    noMediaList: (ArrayList<String>, String) -> Unit = ::addingifExsist
    ): List<String> {
        cur ?: return emptyList()
        val noMediaFolders = ArrayList<String>()
        cur.use {
            if (cur.moveToFirst()) {
                do {
                    val path = cur.getString(cur.getColumnIndex(MediaStore.Files.FileColumns.DATA)) ?: continue
                    noMediaList.invoke(noMediaFolders, path)
                } while (cur.moveToNext())
            }
        }
        return noMediaFolders
    }
}

fun addingifExsist(noMediaList: ArrayList<String>, path: String) {
    val noMediaFile = File(path)
    if (noMediaFile.exists())
        noMediaList.add("${noMediaFile.parent}/")
}