package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by taesu on 2017-11-13.
 */
class MediaTypeFilterTest {

    var filter = MediaTypeFilter(mockConfig(IMAGES))
    @Test
    fun `단일 Image Type 검증`() {
        filter = MediaTypeFilter(mockConfig(IMAGES))
        fun imageTest() {
            assertThat(filter.filterd(".jpg"), `is`(false))
            assertThat(filter.filterd(".png"), `is`(false))
            assertThat(filter.filterd(".jpeg"), `is`(false))
            assertThat(filter.filterd(".bmp"), `is`(false))
            assertThat(filter.filterd(".webp"), `is`(false))
        }

        fun notImageTest() {
            assertThat(filter.filterd(".abc"), `is`(true))
            assertThat(filter.filterd(".mp4"), `is`(true))
            assertThat(filter.filterd(".gif"), `is`(true))
        }
        imageTest()
        notImageTest()
    }

    @Test
    fun `단일 Video Type 검증`() {
        filter = MediaTypeFilter(mockConfig(VIDEOS))
        fun videoTest() {
            assertThat(filter.filterd(".mp4"), `is`(false))
            assertThat(filter.filterd(".mkv"), `is`(false))
            assertThat(filter.filterd(".webm"), `is`(false))
            assertThat(filter.filterd(".avi"), `is`(false))
            assertThat(filter.filterd(".3gp"), `is`(false))
            assertThat(filter.filterd(".mov"), `is`(false))
            assertThat(filter.filterd(".m4v"), `is`(false))
            assertThat(filter.filterd(".3gpp"), `is`(false))
        }

        fun notVideoTest() {
            assertThat(filter.filterd(".gif"), `is`(true))
            assertThat(filter.filterd(".jpg"), `is`(true))
        }
        videoTest()
        notVideoTest()
    }

    @Test
    fun `단일 GIF Type 검증`() {
        filter = MediaTypeFilter(mockConfig(GIFS))

        fun gifTest() {
            assertThat(filter.filterd(".gif"), `is`(false))
        }

        fun notGifTest() {
            assertThat(filter.filterd(".m4v"), `is`(true))
            assertThat(filter.filterd(".3gpp"), `is`(true))
            assertThat(filter.filterd(".jpg"), `is`(true))
        }
        gifTest()
        notGifTest()
    }

    @Test
    fun `복합 IMAGE VIDEO Type 검증`() {
        filter = MediaTypeFilter(mockConfig(IMAGES or VIDEOS))

        fun imageVideoTest() {
            assertThat(filter.filterd(".jpg"), `is`(false))
            assertThat(filter.filterd(".mp4"), `is`(false))
        }

        fun notimageVideoTest() {
            assertThat(filter.filterd(".abc"), `is`(true))
            assertThat(filter.filterd(".gif"), `is`(true))
        }
        imageVideoTest()
        notimageVideoTest()
    }

    @Test
    fun `복합 IMAGE VIDEO GIF Type 검증`() {
        filter = MediaTypeFilter(mockConfig(IMAGES or VIDEOS or GIFS))

        fun imageVideoGifTest() {
            assertThat(filter.filterd(".jpg"), `is`(false))
            assertThat(filter.filterd(".mp4"), `is`(false))
            assertThat(filter.filterd(".gif"), `is`(false))
        }

        fun notimageVideoGifTest() {
            assertThat(filter.filterd(".abc"), `is`(true))
            assertThat(filter.filterd(".hpp"), `is`(true))
        }
        imageVideoGifTest()
        notimageVideoGifTest()
    }

    private fun mockConfig(mediaType: Int): ApplicationConfig {
        val config = mock<ApplicationConfig>()
        whenever(config.filterdType).thenReturn(mediaType)
        return config
    }
}