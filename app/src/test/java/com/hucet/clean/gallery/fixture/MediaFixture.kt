package com.hucet.clean.gallery.fixture

import android.database.Cursor
import android.provider.MediaStore
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import java.io.File

/**
 * Created by taesu on 2017-10-31.
 */

// for non local var
private var currentIndex = 0

class MediaFixture {

    private val gson = GsonBuilder()
            .create()

    fun getMediaFromJson(path: String): Cursor {
        currentIndex = 0
        return getCursor(readJson(path))
    }

    private fun readJson(path: String): String {
        val url = this.javaClass.classLoader.getResource(path)
        val file = File(url.file)
        return file.readText()
    }

    private fun getCursor(json: String): Cursor {
        val medias = fakeMedia(json)
        return mockCursor(medias)
    }

    private fun mockCursor(medias: List<FakeMedia>): Cursor {
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
        whenever(c.getColumnIndex(MediaStore.Images.Media.DATA)).thenReturn(MediaColumnOfIndex.DATA.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)).thenReturn(MediaColumnOfIndex.DISPLAY_NAME.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.SIZE)).thenReturn(MediaColumnOfIndex.SIZE.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)).thenReturn(MediaColumnOfIndex.DATE_TAKEN.getValue())
        whenever(c.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(MediaColumnOfIndex.DATE_MODIFIED.getValue())

        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)).thenReturn(MediaColumnOfIndex.DATA.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)).thenReturn(MediaColumnOfIndex.DISPLAY_NAME.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)).thenReturn(MediaColumnOfIndex.SIZE.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)).thenReturn(MediaColumnOfIndex.DATE_TAKEN.getValue())
        whenever(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(MediaColumnOfIndex.DATE_MODIFIED.getValue())
    }


    private fun changeCursorProperties(cursor: Cursor, newMedia: FakeMedia) {
        mockColumnIndex(cursor)
        whenever(cursor?.getString(MediaColumnOfIndex.DATA.getValue())).thenReturn(newMedia.data)
        whenever(cursor?.getString(MediaColumnOfIndex.DISPLAY_NAME.getValue())).thenReturn(newMedia.displayName)
        whenever(cursor?.getLong(MediaColumnOfIndex.SIZE.getValue())).thenReturn(newMedia.size)
        whenever(cursor?.getLong(MediaColumnOfIndex.DATE_TAKEN.getValue())).thenReturn(newMedia.dateTaken)
        whenever(cursor?.getLong(MediaColumnOfIndex.DATE_MODIFIED.getValue())).thenReturn(newMedia.dateModified)
    }

    private enum class MediaColumnOfIndex(private val i: Int) {
        DISPLAY_NAME(1),
        DATE_TAKEN(2),
        DATE_MODIFIED(3),
        DATA(4),
        SIZE(5);

        fun getValue() = i
    }

    private fun fakeMedia(json: String): List<FakeMedia> {
        val fakeMediaType = object : TypeToken<List<FakeMedia>>() {}.type
        return gson.fromJson<List<FakeMedia>>(json, fakeMediaType)
    }

    private data class FakeMedia(val dateModified: Long,
                                 val data: String,
                                 val displayName: String,
                                 val size: Long,
                                 val dateTaken: Long)

}