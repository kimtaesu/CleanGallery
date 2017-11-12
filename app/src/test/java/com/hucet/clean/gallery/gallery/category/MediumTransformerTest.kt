package com.hucet.clean.gallery.gallery.category

import android.os.Environment
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.extension.externalStorageDirectory
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

/**
 * Created by taesu on 2017-11-12.
 */
val externalStoragePath = "ROOT"

class MediumTransformerTest {
    var date: DateClassifier = mock()
    var dir: DirClassifier = mock()
    var transformer = TestMediumTransformer(date, dir)

    @Before
    fun setUp() {
        date = mock<DateClassifier>()
        dir = mock<DirClassifier>()
        transformer = TestMediumTransformer(date, dir)
    }

    @Test
    fun `Date Claasifier 호출 검증`() {
        transformer.transform(listOf(), externalStoragePath, false)
        verify(dir, never()).classify(any(), any())
        verify(date, times(1)).classify(any(), any())
    }

    @Test
    fun `Dir Claasifier 호출 검증`() {
        transformer.transform(listOf(), externalStoragePath, true)
        verify(dir, times(1)).classify(any(), any())
        verify(date, never()).classify(any(), any())
    }

    @Test
    fun `Medium 호출 검증`() {
        transformer.transform(listOf(), "Not matchs to the external storae", true)
        verify(dir, never()).classify(any(), any())
        verify(date, never()).classify(any(), any())
    }


}

class TestMediumTransformer(dateClassifier: DateClassifier,
                            dirClassifier: DirClassifier) : MediumTransformer(dateClassifier, dirClassifier) {
    override fun isExternalStorage(curPath: String): Boolean {
        return curPath == externalStoragePath
    }
}