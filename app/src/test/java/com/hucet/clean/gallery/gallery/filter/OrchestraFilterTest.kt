package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be`
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-26.
 */
class OrchestraFilterTest : SubjectSpek<OrchestraFilter>({

    val filters by memoized { HashSet<MediaTypeFilter>() }

    given("OrchestraFilter")
    {
        subject {
            OrchestraFilter(filters)
        }
        on("filter [FILTERED, FILTERED]")
        {
            filters.addAll(mockFilters(FILTERED, FILTERED))
            val result = subject.filterd(MediumFixture.medium(), emptySet(), ReadOnlyConfigsFixture.readOnlyConfigs())
            it("result FILTERED")
            {
                result `should be` FILTERED
            }
        }
        on("filter [NOT_FILTERED, FILTERED]")
        {
            filters.addAll(mockFilters(NOT_FILTERED, FILTERED))
            val result = subject.filterd(MediumFixture.medium(), emptySet(), ReadOnlyConfigsFixture.readOnlyConfigs())
            it("result FILTERED")
            {
                result `should be` FILTERED
            }
        }
        on("filter [NOT_FILTERED, NOT_FILTERED]")
        {
            filters.addAll(mockFilters(NOT_FILTERED, NOT_FILTERED))
            val result = subject.filterd(MediumFixture.medium(), emptySet(), ReadOnlyConfigsFixture.readOnlyConfigs())
            it("result NOT_FILTERED")
            {
                result `should be` NOT_FILTERED
            }
        }
    }
})

private fun mockFilters(vararg isFilters: Boolean): Set<MediaTypeFilter> {
    val filters = HashSet<MediaTypeFilter>()
    isFilters.forEach {
        val filter = mock<ImageVideoGifFilter>()
        whenever(filter.filterd(any(), any())).thenReturn(it)
        filters.add(filter)
    }
    return filters
}
