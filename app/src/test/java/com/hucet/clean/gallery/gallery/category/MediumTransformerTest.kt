package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.config.ApplicationConfig
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
    val config by memoized { mock<ApplicationConfig>() }
    given("a mediumTransformer")
    {
        subject {
            TestMediumTransformer(dateMock, dirMock, config)
        }

        on("a dateClaasifier call 검증 ")
        {
            whenever(config.categoryMode).thenReturn(CategoryMode.DATE)
            whenever(config.sortOptionType).thenReturn(SortOptionType.BY_DAILY)
            subject.transform(listOf(), externalStoragePath)
            it("one calll dateCalssify, never call dirClassify")
            {

                verify(dirMock, never()).classify(any(), any())
                verify(dateMock, times(1)).classify(any(), any())
            }
        }
        on("a dirClaasifier call 검증 ")
        {
            whenever(config.categoryMode).thenReturn(CategoryMode.DIRECTORY)
            whenever(config.sortOptionType).thenReturn(SortOptionType.BY_NAME)
            subject.transform(listOf(), externalStoragePath)
            it("one calll dirClassify, never call dateClassify")
            {
                verify(dirMock, times(1)).classify(any(), any())
                verify(dateMock, never()).classify(any(), any())
            }
        }
        on("a medium call 검증")
        {
            whenever(config.categoryMode).thenReturn(CategoryMode.DIRECTORY)
            whenever(config.sortOptionType).thenReturn(SortOptionType.BY_NAME)
            subject.transform(listOf(), "Not matchs to the external storae")
            it("never calll dirClassify, never call dateClassify")
            {
                verify(dirMock, never()).classify(any(), any())
                verify(dateMock, never()).classify(any(), any())
            }
        }
    }
})

class TestMediumTransformer(dateClassifier: DateClassifier,
                            dirClassifier: DirClassifier,
                            config: ApplicationConfig) : MediumTransformer(dateClassifier, dirClassifier, config) {
    override fun isExternalStorage(curPath: String): Boolean {
        return curPath == externalStoragePath
    }
}