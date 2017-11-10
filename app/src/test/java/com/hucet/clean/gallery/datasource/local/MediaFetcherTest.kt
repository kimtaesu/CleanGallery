package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.FakeMedium
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by taesu on 2017-10-31.
 */

class MediaFetcherTest {
    var mediaFetcher = MediaFetcher(mockContext(), mockConfig())
    @Before
    fun setUp() {
        mediaFetcher = MediaFetcher(mockContext(), mockConfig())
    }

    @Test
    fun `curosr parser 검증`() {
        val result = getParseCursorFromPath("test_default.json")
        assertThat(result.size, `is`(2))
        assertThat(result.get(0).name, `is`("3_Forest.jpg"))
        assertThat(result.get(1).name, `is`("4_Structure.jpg"))
    }

    @After
    fun after() {
    }

    private fun mockConfig(): ApplicationConfig {
        val config = mock<ApplicationConfig>()
        whenever(config.filterMedia).thenReturn(IMAGES)
        return config
    }

    private fun mockContext(): Context {
        val context = mock<Context>()
        return context
    }

    private fun getParseCursorFromPath(path: String): List<Medium> {
        val cursor = CursorFixture.getCursor(path, "media/test")
        return mediaFetcher.parseCursor(cursor)
    }
}

