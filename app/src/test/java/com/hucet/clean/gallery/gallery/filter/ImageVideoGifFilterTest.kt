package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by taesu on 2017-11-13.
 */
class ImageVideoGifFilterTest {

    var filter = ImageVideoGifFilter(mockConfig(IMAGES))

    private fun getTestMedium(fileName: String): Medium {
        return Medium(1, fileName, "", 1, 1, 1)
    }

    private fun testFilter(fileName: String, result: Boolean) {
        assertThat(filter.filterd(getTestMedium(fileName)), Is.`is`(result))
    }

    @Test
    fun `단일 Image Type 검증`() {
        filter = ImageVideoGifFilter(mockConfig(IMAGES))
        fun imageTest() {
            testFilter(".jpg", false)
            testFilter(".png", false)
            testFilter(".jpeg", false)
            testFilter(".bmp", false)
            testFilter(".webp", false)
        }

        fun notImageTest() {
            testFilter(".abc", true)
            testFilter(".mp4", true)
            testFilter(".gif", true)
        }
        imageTest()
        notImageTest()
    }

    @Test
    fun `단일 Video Type 검증`() {
        filter = ImageVideoGifFilter(mockConfig(VIDEOS))
        fun videoTest() {
            testFilter(".mp4", false)
            testFilter(".mkv", false)
            testFilter(".webm", false)
            testFilter(".avi", false)
            testFilter(".3gp", false)
            testFilter(".m4v", false)
            testFilter(".3gpp", false)
        }

        fun notVideoTest() {
            testFilter(".gif", true)
            testFilter(".jpg", true)
        }
        videoTest()
        notVideoTest()
    }

    @Test
    fun `단일 GIF Type 검증`() {
        filter = ImageVideoGifFilter(mockConfig(GIFS))

        fun gifTest() {
            testFilter(".gif", false)
        }

        fun notGifTest() {
            testFilter(".m4v", true)
            testFilter(".3gpp", true)
            testFilter(".jpg", true)
        }
        gifTest()
        notGifTest()
    }

    @Test
    fun `복합 IMAGE VIDEO Type 검증`() {
        filter = ImageVideoGifFilter(mockConfig(IMAGES or VIDEOS))

        fun imageVideoTest() {
            testFilter(".jpg", false)
            testFilter(".mp4", false)
        }

        fun notimageVideoTest() {
            testFilter(".abc", true)
            testFilter(".gif", true)
        }
        imageVideoTest()
        notimageVideoTest()
    }

    @Test
    fun `복합 IMAGE VIDEO GIF Type 검증`() {
        filter = ImageVideoGifFilter(mockConfig(IMAGES or VIDEOS or GIFS))

        fun imageVideoGifTest() {
            testFilter(".jpg", false)
            testFilter(".mp4", false)
            testFilter(".gif", false)
        }

        fun notimageVideoGifTest() {
            testFilter(".abc", true)
            testFilter(".hpp", true)
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