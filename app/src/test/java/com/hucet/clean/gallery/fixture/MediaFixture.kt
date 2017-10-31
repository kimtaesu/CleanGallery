package com.hucet.clean.gallery.fixture

import android.database.Cursor
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import java.io.File

/**
 * Created by taesu on 2017-10-31.
 */

// for non local var
private var currentIndex = 0

class MediaFixture {

    val gson = GsonBuilder()
            .create()

    fun getMediaFromJson(path: String): Cursor? {
        return getCursor(readJson(path))
    }

    private fun readJson(path: String): String {
        val url = this.javaClass.classLoader.getResource(path)
        val file = File(url.file)
        return file.readText()
    }

    private fun getCursor(json: String): Cursor? {
        val medias = fakeMedia(json)
        return mockMedia(medias)
    }

    private fun mockMedia(medias: List<FakeMedia>): Cursor? {
        val arrayCursor = ArrayList<Cursor>()
        for (media in medias) {
            val cursor = mock<Cursor>()
            whenever(cursor.getColumnIndexOrThrow(any())).thenReturn(any())
            whenever(cursor.getString(MediaColumnOfIndex.DATA.getValue())).thenReturn(media.data)
            whenever(cursor.getString(MediaColumnOfIndex.DISPLAY_NAME.getValue())).thenReturn(media.displayName)
            whenever(cursor.getLong(MediaColumnOfIndex.SIZE.getValue())).thenReturn(media.size)
            whenever(cursor.getLong(MediaColumnOfIndex.DATE_TAKEN.getValue())).thenReturn(media.dateTaken)
            whenever(cursor.getInt(MediaColumnOfIndex.DATE_MODIFIED.getValue())).thenReturn(media.dateModified)
            arrayCursor.add(cursor)
        }

        for (i in 0 until arrayCursor.size) {
            whenever(arrayCursor[i].moveToFirst()).thenAnswer({
                currentIndex = 0
                true
            })
            whenever(arrayCursor[i].moveToNext()).thenAnswer({
                if (currentIndex++ + 1 < arrayCursor.size) {
                    true
                } else {
                    false
                }
            })
        }
        return arrayCursor.first()
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

    private data class FakeMedia(val dateModified: Int,
                                 val data: String,
                                 val displayName: String,
                                 val size: Long,
                                 val dateTaken: Long)

}