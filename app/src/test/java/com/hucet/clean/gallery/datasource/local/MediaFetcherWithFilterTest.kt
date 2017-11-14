package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert

/**
 * Created by taesu on 2017-11-14.
 */

class MediaFetcherWithFilterTest : SubjectSpek<MediaFetcher>({

    describe("filter [NOT_FILTERED, NOT_FILTERED]")
    {
        subject {
            MediaFetcher(mock<Context>(), mockFilters(MediaTypeFilter.NOT_FILTERED, MediaTypeFilter.NOT_FILTERED))
        }

        val items = parseCursor(subject, MediumFixture.DEFAULT)

        it("items size == 2")
        {
            Assert.assertThat(2, Is.`is`(items.size))
        }
    }

    describe("filter [FILTERED, NOT_FILTERED]")
    {
        subject {
            MediaFetcher(mock<Context>(), mockFilters(MediaTypeFilter.FILTERED, MediaTypeFilter.NOT_FILTERED))
        }

        val items = parseCursor(subject, MediumFixture.DEFAULT)

        it("items size == 0")
        {
            Assert.assertThat(0, Is.`is`(items.size))
        }
    }

    describe("filter [FILTERED, FILTERED]")
    {
        subject {
            MediaFetcher(mock<Context>(), mockFilters(MediaTypeFilter.FILTERED, MediaTypeFilter.FILTERED))
        }
        val items = parseCursor(subject, MediumFixture.DEFAULT)

        it("items size == 0")
        {
            Assert.assertThat(0, Is.`is`(items.size))
        }
    }
})


fun mockFilters(vararg isFilters: Boolean): Set<MediaTypeFilter> {
    val mocks = HashSet<MediaTypeFilter>()
    isFilters.forEach {
        val mock = mock<MediaTypeFilter>()
        whenever(mock.filterd(any(), any())).thenReturn(it)
        mocks.add(mock)
    }
    return mocks
}