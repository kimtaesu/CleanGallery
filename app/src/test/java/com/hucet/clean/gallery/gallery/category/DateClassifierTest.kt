package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.DeserializerFixture
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.model.Date
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifierTest {
    val testData = DeserializerFixture.deserializeMedium("test_category_date.json", "media/test")
    val correctDaily = listOf(
            "2017-02-23", "2017-11-06"
    )
    val correctWeekly = listOf(
            "2017-02", "2017-11"
    )
    val correctYearly = listOf(
            "2017"
    )

    @Test
    fun `Date Daily Category 검증`() {
        val dateClassifier = DateClassifier(mockConfig(DateClassifier.DATE_SORT_TYPE.DAILY))
        var result = dateClassifier.classify(testData)
        val temp = result.filter {
            it.viewType == GalleryAdapter.GalleryType.DATE
        }.map {
            it as Date
        }

        temp.forEachIndexed { index, it ->
            assertThat(it.date, `is`(correctDaily[index]))
        }
    }

    @Test
    fun `Date Monthly Category 검증`() {
        val dateClassifier = DateClassifier(mockConfig(DateClassifier.DATE_SORT_TYPE.MONTHLY))
        var result = dateClassifier.classify(testData)
        val temp = result.filter {
            it.viewType == GalleryAdapter.GalleryType.DATE
        }.map {
            it as Date
        }

        temp.forEachIndexed { index, it ->
            assertThat(it.date, `is`(correctWeekly[index]))
        }
    }


    @Test
    fun `Date Yearly Category 검증`() {
        val dateClassifier = DateClassifier(mockConfig(DateClassifier.DATE_SORT_TYPE.YEARLY))
        var result = dateClassifier.classify(testData)
        val temp = result.filter {
            it.viewType == GalleryAdapter.GalleryType.DATE
        }.map {
            it as Date
        }

        temp.forEachIndexed { index, it ->
            assertThat(it.date, `is`(correctYearly[index]))
        }
    }


    private fun mockConfig(type: DateClassifier.DATE_SORT_TYPE): ApplicationConfig {
        val config = mock<ApplicationConfig>()
        whenever(config.getDateSortType(any())).thenReturn(type.value())
        return config
    }
}