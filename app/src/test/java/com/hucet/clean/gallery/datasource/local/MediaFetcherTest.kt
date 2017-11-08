package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.fixture.MediaFixture
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is
import org.hamcrest.core.Is.*
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
    fun `default media json 에 의한 검증`() {
        val result = getParseCursorFromPath("media/default_media.json")
        assertThat(result.size, `is`(2))
        assertThat(result.get(0).name, `is`("3_Forest.jpg"))
        assertThat(result.get(1).name, `is`("4_Structure.jpg"))
    }

    @Test
    fun `Category Dir Type  분류 검증`() {
        val result = getParseCursorFromPath("media/category_media.json")
        val category: Map<String, List<Medium>> = mediaFetcher.category(MediaFetcher.CategoryType.DIR, result)
        assertThat(category.size, Is.`is`(3))
        assertThat(category.get("KakaoTalk")?.size, `is`(2))
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
        val cursor = MediaFixture().getMediaFromJson("media/category_media.json")
        return mediaFetcher.parseCursor(cursor)
    }

}

