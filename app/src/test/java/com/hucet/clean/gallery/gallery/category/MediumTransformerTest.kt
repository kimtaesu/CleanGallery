package com.hucet.clean.gallery.gallery.category

import android.os.Environment
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.extension.externalStorageDirectory
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.*
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

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
            it("one calll dateCalssify, never call dirClassify")
            {
                subject.transform(listOf(), externalStoragePath, false)
                verify(dirMock, never()).classify(any(), any())
                verify(dateMock, times(1)).classify(any(), any())
            }
        }
        on("a dirClaasifier call 검증 ")
        {

            it("one calll dirClassify, never call dateClassify")
            {
                subject.transform(listOf(), externalStoragePath, true)
                verify(dirMock, times(1)).classify(any(), any())
                verify(dateMock, never()).classify(any(), any())
            }
        }
        on("a medium call 검증")
        {
            it("never calll dirClassify, never call dateClassify")
            {
                subject.transform(listOf(), "Not matchs to the external storae", true)
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