package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.fixture.CursorFixture
import com.hucet.clean.gallery.fixture.MediumFixture
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be`
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-24.
 */
class LocalDataSourceTest : SubjectSpek<LocalDataSource>({
    val test = MediumFixture.DEFAULT
    val cursor = CursorFixture.getCursor(test)
    val mediaFetcher by memoized { mock<MediaFetcher>() }
    subject {
        LocalDataSource(mediaFetcher)
    }
    given("LocalDataSource") {

        beforeEachTest {
            whenever(mediaFetcher.query(any())).thenReturn(cursor)
            whenever(mediaFetcher.parseCursor(any())).thenReturn(test)
        }
        on("cacheInvalidate false 2번 호출")
        {
            val testSub = subject.run {
                getGalleries(false).test()
                getGalleries(false).test()
            }
            it("parseCursor은 최초 1회만 호출되어야 함")
            {
                testSub.assertComplete()
                testSub.assertNoErrors()
                verify(mediaFetcher, times(1)).parseCursor(any())
            }
        }
        on("cacheInvalidate true 2번 호출")
        {
            val testSub = subject.run {
                getGalleries(true).test()
                getGalleries(true).test()
            }
            it("parseCursor은 2회만 호출되어야 함")
            {
                testSub.assertComplete()
                testSub.assertNoErrors()
                verify(mediaFetcher, times(2)).parseCursor(any())
            }
        }
    }
})