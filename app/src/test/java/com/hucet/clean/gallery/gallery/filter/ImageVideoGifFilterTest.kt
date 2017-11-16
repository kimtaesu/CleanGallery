package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal to`
import org.hamcrest.core.Is
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert.assertThat

/**
 * Created by taesu on 2017-11-13.
 */

class ImageVideoGifFilterTest : SubjectSpek<ImageVideoGifFilter>({


    describe("a imageVideoGifFilter")
    {
        context("filterType [IMAGES]")
        {
            subject {
                ImageVideoGifFilter(mockConfig(IMAGES))
            }

            it("image tpye NOT_FILTERED")
            {
                testFilter(subject, ".jpg", NOT_FILTERED)
                testFilter(subject, ".png", NOT_FILTERED)
                testFilter(subject, ".jpeg", NOT_FILTERED)
                testFilter(subject, ".bmp", NOT_FILTERED)
                testFilter(subject, ".webp", NOT_FILTERED)
            }
            it("image tpye FILTERED")
            {
                testFilter(subject, ".ic_grid_to_list_animation", FILTERED)
                testFilter(subject, ".mp4", FILTERED)
                testFilter(subject, ".gif", FILTERED)
            }
        }
        on("filterType [VIDEOS]")
        {
            subject {
                ImageVideoGifFilter(mockConfig(VIDEOS))
            }

            it("video type NOT_FILTERED")
            {
                testFilter(subject, ".mp4", NOT_FILTERED)
                testFilter(subject, ".mkv", NOT_FILTERED)
                testFilter(subject, ".webm", NOT_FILTERED)
                testFilter(subject, ".avi", NOT_FILTERED)
                testFilter(subject, ".3gp", NOT_FILTERED)
                testFilter(subject, ".m4v", NOT_FILTERED)
                testFilter(subject, ".3gpp", NOT_FILTERED)
            }
            it("video type FILTERED")
            {
                testFilter(subject, ".gif", FILTERED)
                testFilter(subject, ".jpg", FILTERED)
            }
        }
        on("filterType [GIFS]")
        {
            subject {
                ImageVideoGifFilter(mockConfig(GIFS))
            }

            it("gif type NOT_FILTERED")
            {
                testFilter(subject, ".gif", NOT_FILTERED)
            }
            it("gif type FILTERED")
            {
                testFilter(subject, ".m4v", FILTERED)
                testFilter(subject, ".3gpp", FILTERED)
                testFilter(subject, ".jpg", FILTERED)
            }
        }
        on("filterType [IMAGES or VIDEOS]")
        {
            subject {
                ImageVideoGifFilter(mockConfig(VIDEOS or IMAGES))
            }

            it("image or video type NOT_FILTERED")
            {
                testFilter(subject, ".jpg", NOT_FILTERED)
                testFilter(subject, ".mp4", NOT_FILTERED)
            }
        }
        it("image or video  type FILTERED")
        {
            testFilter(subject, ".aaa", FILTERED)
            testFilter(subject, ".gif", FILTERED)
        }
        on("filterType [IMAGES or VIDEOS or GIFS]")
        {
            subject {
                ImageVideoGifFilter(mockConfig(VIDEOS or IMAGES or GIFS))
            }

            it("image or video or gif type NOT_FILTERED")
            {
                testFilter(subject, ".jpg", NOT_FILTERED)
                testFilter(subject, ".mp4", NOT_FILTERED)
                testFilter(subject, ".gif", NOT_FILTERED)
            }
        }
        it("image or video or gif  type FILTERED")
        {
            testFilter(subject, ".aaa", FILTERED)
            testFilter(subject, ".hpp", FILTERED)
        }
    }
})

fun mockConfig(mediaType: Int): ApplicationConfig {
    val config = mock<ApplicationConfig>()
    whenever(config.filterdType).thenReturn(mediaType)
    return config
}

fun testFilter(filter: ImageVideoGifFilter, ext: String, result: Boolean) {
    val medium = MediumFixture.medium(name = ext)
    result `should be` filter.filterd(medium, emptySet())
}