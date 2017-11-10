package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.fixture.DeserializerFixture
import org.hamcrest.core.Is.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifierTest {
    var dateClassifier = DateClassifier()
    val testData = DeserializerFixture.deserializeMedium("test_category_date.json", "media/test")
    val correctIds: List<Long> = listOf(
            0, 1, 2, 0, 12111, 1242, 123
    )

    @Before
    fun setUp() {
        dateClassifier = DateClassifier()
    }

    @Test
    fun `Date Category 검증`() {
        var result = dateClassifier.category(testData)
        result.forEachIndexed { index, it ->
            assertThat(it.id, `is`(correctIds[index]))
        }
    }
}