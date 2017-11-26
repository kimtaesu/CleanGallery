package com.hucet.clean.gallery.datasource.local

import android.content.Context
import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.gallery.filter.OrchestraFilter
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be`
import org.hamcrest.core.Is
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert

/**
 * Created by taesu on 2017-11-14.
 */

class MediaFetcherWithFilterTest : SubjectSpek<TestMediaFetcher>({
    val orchestraFilter by memoized { mock<OrchestraFilter>() }

    given("MediaFetcher")
    {
        subject {
            TestMediaFetcher(mock<Context>(), orchestraFilter)
        }

        on("orchestraFilter NOT_FILTERED")
        {
            whenever(orchestraFilter.filterd(any(), any())).thenReturn(MediaTypeFilter.NOT_FILTERED)
            val items = parseCursor(subject, MediumFixture.DEFAULT)

            it("item size == 2")
            {
                items.size `should be` 2
            }
        }
        on("orchestraFilter FILTERED")
        {
            whenever(orchestraFilter.filterd(any(), any())).thenReturn(MediaTypeFilter.FILTERED)
            val items = parseCursor(subject, MediumFixture.DEFAULT)

            it("item size == 0")
            {
                items.size `should be` 0
            }
        }
    }
})

private fun parseCursor(subject: MediaFetcher, items: List<Medium>): List<Medium> {
    val cursor = CursorFixture.getCursor(MediumFixture.DEFAULT)
    return subject.parseCursor(cursor, emptySet())
}
