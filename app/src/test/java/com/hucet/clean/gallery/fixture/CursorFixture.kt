package com.hucet.clean.gallery.fixture

import android.database.Cursor
import android.provider.MediaStore
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever

/**
 * Created by taesu on 2017-10-31.
 */

// for non local var
private var currentIndex = 0

object CursorFixture {
    fun getCursor(path: String, parent: String): Cursor {
        currentIndex = 0
        return mockCursor(DeserializerFixture.deserializeMedium(path, parent))
    }

    fun getCursor(medias: List<Medium>): Cursor {
        currentIndex = 0
        return mockCursor(medias)
    }

    private fun mockCursor(medias: List<Medium>): Cursor {
        var cursor = mock<Cursor>()
        changeCursorProperties(cursor, medias[currentIndex])

        whenever(cursor?.moveToFirst()).thenAnswer({
            currentIndex = 0
            changeCursorProperties(cursor, medias[currentIndex])
            true
        })
        whenever(cursor?.moveToNext()).thenAnswer({
            if (currentIndex++ + 1 < medias.size) {
                changeCursorProperties(cursor, medias[currentIndex])
                true
            } else {
                false
            }
        })
        return cursor
    }

    private fun mockColumnIndex(c: Cursor) {
        whenever(c.getColumnIndex(MediaStore.Images.Media._ID)).thenReturn(MediaColumnOfIndex.ID.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.DATA)).thenReturn(MediaColumnOfIndex.DATA.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)).thenReturn(MediaColumnOfIndex.DISPLAY_NAME.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.SIZE)).thenReturn(MediaColumnOfIndex.SIZE.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)).thenReturn(MediaColumnOfIndex.DATE_TAKEN.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(MediaColumnOfIndex.DATE_MODIFIED.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.ORIENTATION)).thenReturn(MediaColumnOfIndex.ORIENTATION.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)).thenReturn(MediaColumnOfIndex.MIME_TYPE.getValue())

        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)).thenReturn(MediaColumnOfIndex.ID.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).thenReturn(MediaColumnOfIndex.DATA.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)).thenReturn(MediaColumnOfIndex.DISPLAY_NAME.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)).thenReturn(MediaColumnOfIndex.SIZE.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)).thenReturn(MediaColumnOfIndex.DATE_TAKEN.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(MediaColumnOfIndex.DATE_MODIFIED.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION)).thenReturn(MediaColumnOfIndex.ORIENTATION.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)).thenReturn(MediaColumnOfIndex.MIME_TYPE.getValue())

    }


    private fun changeCursorProperties(cursor: Cursor, newMedia: Medium) {
        mockColumnIndex(cursor)
        whenever(cursor?.getString(MediaColumnOfIndex.DATA.getValue())).thenReturn(newMedia.path)
        whenever(cursor?.getString(MediaColumnOfIndex.DISPLAY_NAME.getValue())).thenReturn(newMedia.name)
        whenever(cursor?.getLong(MediaColumnOfIndex.SIZE.getValue())).thenReturn(newMedia.size)
        whenever(cursor?.getLong(MediaColumnOfIndex.DATE_TAKEN.getValue())).thenReturn(newMedia.taken)
        whenever(cursor?.getLong(MediaColumnOfIndex.DATE_MODIFIED.getValue())).thenReturn(newMedia.modified)
    }

    private enum class MediaColumnOfIndex(private val i: Int) {
        ID(0),
        DISPLAY_NAME(1),
        DATE_TAKEN(2),
        DATE_MODIFIED(3),
        DATA(4),
        SIZE(5),
        ORIENTATION(6),
        MIME_TYPE(7);

        fun getValue() = i
    }


}