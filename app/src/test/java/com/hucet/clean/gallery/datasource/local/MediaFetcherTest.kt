package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
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
    var mediaFetcher = MediaFetcher(mockContext(), mockFilters())
    @Before
    fun setUp() {
        mediaFetcher = MediaFetcher(mockContext(), mockFilters())
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

    private fun mockContext(): Context {
        val context = mock<Context>()
        return context
    }

    private fun mockFilters(): Set<MediaTypeFilter> {
        return setOf(mock<MediaTypeFilter>(), mock<MediaTypeFilter>())
    }

    private fun getParseCursorFromPath(path: String): List<Medium> {
        val cursor = CursorFixture.getCursor(path, "media/test")
        return mediaFetcher.parseCursor(cursor)
    }
}

