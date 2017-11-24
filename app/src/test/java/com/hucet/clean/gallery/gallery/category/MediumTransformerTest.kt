package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.nhaarman.mockito_kotlin.*
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-12.
 */

class MediumTransformerTest : SubjectSpek<MediumTransformer>({
    val dirMock by memoized { mock<DirClassifier>() }
    val dateMock by memoized { mock<DateClassifier>() }
    given("a mediumTransformer")
    {
        subject {
            MediumTransformer(dateMock, dirMock)
        }

        on("a dateClaasifier call 검증 ")
        {
            val mock = ReadOnlyConfigsFixture.mockReadOnlyConfigs(CategoryMode.DATE, sortOptionType = SortOptions(SortOptions.SORT_TYPE.DAILY, SortOptions.ORDER_BY.DESC))
            subject.transform(listOf(), true, mock)
            it("one calll dateCalssify, never call dirClassify")
            {

                verify(dirMock, never()).classify(any(), any())
                verify(dateMock, times(1)).classify(any(), any())
            }
        }
        on("a dirClaasifier call 검증 ")
        {
            val mock = ReadOnlyConfigsFixture.mockReadOnlyConfigs(CategoryMode.DIRECTORY, sortOptionType = SortOptions(SortOptions.SORT_TYPE.NAME, SortOptions.ORDER_BY.DESC))
            subject.transform(listOf(), true, mock)

            it("one calll dirClassify, never call dateClassify")
            {
                verify(dirMock, times(1)).classify(any(), any())
                verify(dateMock, never()).classify(any(), any())
            }
        }
        on("a medium call 검증")
        {
            val mock = ReadOnlyConfigsFixture.mockReadOnlyConfigs(CategoryMode.DIRECTORY, sortOptionType = SortOptions(SortOptions.SORT_TYPE.NAME, SortOptions.ORDER_BY.DESC))
            subject.transform(listOf(), false, mock)
            it("never calll dirClassify, never call dateClassify")
            {
                verify(dirMock, never()).classify(any(), any())
                verify(dateMock, never()).classify(any(), any())
            }
        }
    }
})