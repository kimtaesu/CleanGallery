package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.ReadOnlyConfigsFixture
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.nhaarman.mockito_kotlin.*
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-12.
 */
val externalStoragePath = "ROOT"

class MediumTransformerTest : SubjectSpek<MediumTransformer>({
    val dirMock by memoized { mock<DirClassifier>() }
    val dateMock by memoized { mock<DateClassifier>() }
    given("a mediumTransformer")
    {
        subject {
            TestMediumTransformer(dateMock, dirMock)
        }

        on("a dateClaasifier call 검증 ")
        {
            val mock = ReadOnlyConfigsFixture.mockReadOnlyConfigs(CategoryMode.DATE, sortOptionType = SortOptionType.BY_DAILY)
            subject.transform(listOf(), externalStoragePath, mock)
            it("one calll dateCalssify, never call dirClassify")
            {

                verify(dirMock, never()).classify(any(), any())
                verify(dateMock, times(1)).classify(any(), any())
            }
        }
        on("a dirClaasifier call 검증 ")
        {
            val mock = ReadOnlyConfigsFixture.mockReadOnlyConfigs(CategoryMode.DIRECTORY, sortOptionType = SortOptionType.BY_NAME)
            subject.transform(listOf(), externalStoragePath, mock)

            it("one calll dirClassify, never call dateClassify")
            {
                verify(dirMock, times(1)).classify(any(), any())
                verify(dateMock, never()).classify(any(), any())
            }
        }
        on("a medium call 검증")
        {
            val mock = ReadOnlyConfigsFixture.mockReadOnlyConfigs(CategoryMode.DIRECTORY, sortOptionType = SortOptionType.BY_NAME)
            subject.transform(listOf(), "Not matchs to the external storae", mock)
            it("never calll dirClassify, never call dateClassify")
            {
                verify(dirMock, never()).classify(any(), any())
                verify(dateMock, never()).classify(any(), any())
            }
        }
    }
})

class TestMediumTransformer(dateClassifier: DateClassifier,
                            dirClassifier: DirClassifier) : MediumTransformer(dateClassifier, dirClassifier) {
    override fun isExternalStorage(curPath: String): Boolean {
        return curPath == externalStoragePath
    }
}