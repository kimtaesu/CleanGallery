package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.fixture.FakeMedium
import org.junit.Before
import org.junit.Test

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifierTest {
    var dateClassification = DateClassifier()
    val testData = FakeMedium.deserializeResource("medium_category_date.json", "media/test")
    @Before
    fun setUp() {
        dateClassification = DateClassifier()
    }

    @Test
    fun `aa`() {
    }


}