package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be`
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-13.
 */

class ImageVideoGifFilterTest : SubjectSpek<ImageVideoGifFilter>({
    describe("a imageVideoGifFilter")
    {
        context("filterType [IMAGES]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("image tpye NOT_FILTERED")
            {
                testFilter(NOT_FILTERED, subject, ".jpg", IMAGES)
                testFilter(NOT_FILTERED, subject, ".png", IMAGES)
                testFilter(NOT_FILTERED, subject, ".jpeg", IMAGES)
                testFilter(NOT_FILTERED, subject, ".bmp", IMAGES)
                testFilter(NOT_FILTERED, subject, ".webp", IMAGES)
            }
            it("image tpye FILTERED")
            {
                testFilter(FILTERED, subject, ".ic_grid_to_list_animation", IMAGES)
                testFilter(FILTERED, subject, ".mp4", IMAGES)
                testFilter(FILTERED, subject, ".gif", IMAGES)
            }
        }
        on("filterType [VIDEOS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("video type NOT_FILTERED")
            {
                testFilter(NOT_FILTERED, subject, ".mp4", VIDEOS)
                testFilter(NOT_FILTERED, subject, ".mkv", VIDEOS)
                testFilter(NOT_FILTERED, subject, ".webm", VIDEOS)
                testFilter(NOT_FILTERED, subject, ".avi", VIDEOS)
                testFilter(NOT_FILTERED, subject, ".3gp", VIDEOS)
                testFilter(NOT_FILTERED, subject, ".m4v", VIDEOS)
                testFilter(NOT_FILTERED, subject, ".3gpp", VIDEOS)
            }
            it("video type FILTERED")
            {
                testFilter(FILTERED, subject, ".gif", VIDEOS)
                testFilter(FILTERED, subject, ".jpg", VIDEOS)
            }
        }
        on("filterType [GIFS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("gif type NOT_FILTERED")
            {

                testFilter(NOT_FILTERED, subject, ".gif", GIFS)
            }
            it("gif type FILTERED")
            {
                testFilter(FILTERED, subject, ".m4v", GIFS)
                testFilter(FILTERED, subject, ".3gpp", GIFS)
                testFilter(FILTERED, subject, ".jpg", GIFS)
            }
        }
        on("filterType [IMAGES or VIDEOS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("image or video type NOT_FILTERED")
            {
                testFilter(NOT_FILTERED, subject, ".jpg", VIDEOS or IMAGES)
                testFilter(NOT_FILTERED, subject, ".mp4", VIDEOS or IMAGES)
            }
        }
        it("image or video  type FILTERED")
        {
            testFilter(FILTERED, subject, ".aaa", VIDEOS or IMAGES)
            testFilter(FILTERED, subject, ".gif", VIDEOS or IMAGES)
        }
        on("filterType [IMAGES or VIDEOS or GIFS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("image or video or gif type NOT_FILTERED")
            {
                testFilter(NOT_FILTERED, subject, ".jpg", VIDEOS or IMAGES or GIFS)
                testFilter(NOT_FILTERED, subject, ".mp4", VIDEOS or IMAGES or GIFS)
                testFilter(NOT_FILTERED, subject, ".gif", VIDEOS or IMAGES or GIFS)
            }
        }
        it("image or video or gif  type FILTERED")
        {
            testFilter(FILTERED, subject, ".aaa", VIDEOS or IMAGES or GIFS)
            testFilter(FILTERED, subject, ".hpp", VIDEOS or IMAGES or GIFS)
        }
    }
})

fun testFilter(result: Boolean, filter: ImageVideoGifFilter, ext: String, filterBit: Long) {
    val medium = MediumFixture.medium(name = ext)
    result `should be` filter.filterd(medium, filterBit)
}