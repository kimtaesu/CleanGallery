package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Created by taesu on 2017-11-13.
 */
class NoMediaFolderProviderTest {
    var noMediaProvider = NoMediaFolderProvider(mockContext())
    val testData = DeserializerFixture.deserializeMedium("test_no_media.json", "media/test")
    @Before
    fun setUp() {
        noMediaProvider = NoMediaFolderProvider(mockContext())
    }

    @Test
    fun `NoMediaProvider parseCursor 검증`() {
        val cursor = CursorFixture.getCursor(testData)
        noMediaProvider.parseCursor(cursor, ::testAddingifExsist)
        assertThat(testData, Is.`is`(testData))
    }

    private fun mockContext(): Context {
        val context = mock<Context>()
        return context
    }
}

fun testAddingifExsist(noMediaList: HashSet<String>, path: String) {
    noMediaList.add(path)
}