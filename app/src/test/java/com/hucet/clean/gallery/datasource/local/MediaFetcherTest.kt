package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.provider.MediaStore
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.fixture.MediaFixture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is
import org.hamcrest.core.Is.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by taesu on 2017-10-31.
 */
class MediaFetcherTest {
    @Test
    fun `default media json 에 의한 검증`() {
        val mediaFetcher = MediaFetcher(mockContext(), mockConfig())
        val cursor = MediaFixture().getMediaFromJson("media/default_media.json")
        val result = mediaFetcher.parseCursor(cursor)
        assertThat(result.size, `is`(2))
        assertThat(result.get(0).name, `is`("3_Forest.jpg"))
        assertThat(result.get(1).name, `is`("4_Structure.jpg"))
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
}

