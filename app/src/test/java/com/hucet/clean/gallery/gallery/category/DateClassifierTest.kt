package com.hucet.clean.gallery.gallery.category

import com.google.gson.Gson
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.model.Date
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifierTest {
    var dateClassifier = DateClassifier(mockConfig())
    val testData = DeserializerFixture.deserializeMedium("test_category_date.json", "media/test")
    val correctDates = listOf(
            "2017-02-23", "2017-11-06"
    )

    @Before
    fun setUp() {
        dateClassifier = DateClassifier(mockConfig())
    }

    @Test
    fun `Date Category 검증`() {
        var result = dateClassifier.category(testData)
        val temp = result.filter {
            it.viewType == GalleryAdapter.GalleryType.DATE
        }.map {
            it as Date
        }

        temp.forEachIndexed { index, it ->
            assertThat(it.date, `is`(correctDates[index]))
        }
    }

    private fun mockConfig(): ApplicationConfig {
        val config = mock<ApplicationConfig>()
        whenever(config.dateSortType).thenReturn(DateClassifier.DATE_SORT_TYPE.DAILY.value())
        return config
    }
}