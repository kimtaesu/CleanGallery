package com.hucet.clean.gallery.gallery.filter

import android.media.Image
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.nhaarman.mockito_kotlin.any
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
                testFilter(subject, ".jpg", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
                testFilter(subject, ".png", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
                testFilter(subject, ".jpeg", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
                testFilter(subject, ".bmp", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
                testFilter(subject, ".webp", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
            }
            it("image tpye FILTERED")
            {
                testFilter(subject, ".ic_grid_to_list_animation", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
                testFilter(subject, ".mp4", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
                testFilter(subject, ".gif", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = IMAGES))
            }
        }
        on("filterType [VIDEOS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("video type NOT_FILTERED")
            {
                testFilter(subject, ".mp4", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
                testFilter(subject, ".mkv", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
                testFilter(subject, ".webm", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
                testFilter(subject, ".avi", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
                testFilter(subject, ".3gp", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
                testFilter(subject, ".m4v", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
                testFilter(subject, ".3gpp", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
            }
            it("video type FILTERED")
            {
                testFilter(subject, ".gif", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
                testFilter(subject, ".jpg", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS))
            }
        }
        on("filterType [GIFS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("gif type NOT_FILTERED")
            {

                testFilter(subject, ".gif", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = GIFS))
            }
            it("gif type FILTERED")
            {
                testFilter(subject, ".m4v", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = GIFS))
                testFilter(subject, ".3gpp", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = GIFS))
                testFilter(subject, ".jpg", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = GIFS))
            }
        }
        on("filterType [IMAGES or VIDEOS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("image or video type NOT_FILTERED")
            {
                testFilter(subject, ".jpg", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES))
                testFilter(subject, ".mp4", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES))
            }
        }
        it("image or video  type FILTERED")
        {
            testFilter(subject, ".aaa", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES))
            testFilter(subject, ".gif", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES))
        }
        on("filterType [IMAGES or VIDEOS or GIFS]")
        {
            subject {
                ImageVideoGifFilter()
            }

            it("image or video or gif type NOT_FILTERED")
            {
                testFilter(subject, ".jpg", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES or GIFS))
                testFilter(subject, ".mp4", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES or GIFS))
                testFilter(subject, ".gif", NOT_FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES or GIFS))
            }
        }
        it("image or video or gif  type FILTERED")
        {
            testFilter(subject, ".aaa", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES or GIFS))
            testFilter(subject, ".hpp", FILTERED, ReadOnlyConfigsFixture.mockReadOnlyConfigs(filterBit = VIDEOS or IMAGES or GIFS))
        }
    }
})

fun mockReadOnlyConfig(mediaType: Long): ReadOnlyConfigs {
    val mock = mock<ReadOnlyConfigs>()
    whenever(mock.getFilterBit()).thenReturn(mediaType)
    return mock
}

fun testFilter(filter: ImageVideoGifFilter, ext: String, result: Boolean, readOnlyConfigs: ReadOnlyConfigs) {
    val medium = MediumFixture.medium(name = ext)
    result `should be` filter.filterd(medium, emptySet(), readOnlyConfigs)
}