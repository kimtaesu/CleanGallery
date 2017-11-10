package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.fixture.FakeMedium
import org.junit.Before
import org.junit.Test

/**
 * Created by taesu on 2017-11-10.
 */
class DirClassifierTest {
    val testData = FakeMedium.deserializeResource("test_category_dir.json", "media/test")
    var dirClassifier = DirClassifier()

    @Before
    fun setUp() {
        dirClassifier = DirClassifier()
    }

    @Test
    fun `aa`() {
        val result = dirClassifier.category(testData)
    }
}