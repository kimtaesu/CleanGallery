package com.hucet.clean.gallery.datasource.local

import android.content.Context
import android.provider.MediaStore
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.fixture.MediaFixture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

/**
 * Created by taesu on 2017-10-31.
 */
class MediaFetcherTest {
    @Test
    fun `default media json 에 의한 검증`() {
        val mediaFetcher = MediaFetcher(mockContext(), mockConfig())
        val cursor = MediaFixture().getMediaFromJson("media/default_media.json")
        val result = mediaFetcher.getFilesFrom(cursor, "", false, false, arrayListOf())
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

