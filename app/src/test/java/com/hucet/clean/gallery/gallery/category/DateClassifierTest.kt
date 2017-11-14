package com.hucet.clean.gallery.gallery.category

import com.google.gson.Gson
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.TestData
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.category.DateClassifier.DATE_SORT_TYPE.*
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should equal to`
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-10.
 */

fun mockConfig(type: DateClassifier.DATE_SORT_TYPE): ApplicationConfig {
    val config = mock<ApplicationConfig>()
    whenever(config.getDateSortType(any())).thenReturn(type.value())
    return config
}

fun getTestDate(type: DateClassifier.DATE_SORT_TYPE): TestData {
    when (type) {
        DAILY -> {
            return MediumFixture.TEST_CATEGORY_DAILY
        }
        DateClassifier.DATE_SORT_TYPE.MONTHLY -> {
            return MediumFixture.TEST_CATEGORY_MONTHLY
        }
        DateClassifier.DATE_SORT_TYPE.YEARLY -> {
            return MediumFixture.TEST_CATEGORY_YEARLY
        }
    }
}

fun classify(d: DateClassifier, test: List<Medium>): List<Date> {
    var result = d.classify(test)
    return result.filter {
        it.viewType == GalleryType.DATE
    }.map {
        it as Date
    }
}

class DateClassifierTest : SubjectSpek<DateClassifier>({
    given("a dateClassifier")
    {
        on("daily")
        {
            subject {
                DateClassifier(mockConfig(DAILY))
            }
            val (test, correct) = getTestDate(DAILY)
            correct as List<Date>

            var result = classify(subject, test)
            it("daily [2016-01-01, 2017-02-23, 2017-11-06]")
            {

                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("monthly")
        {
            subject {
                DateClassifier(mockConfig(MONTHLY))
            }
            val (test, correct) = getTestDate(MONTHLY)
            correct as List<Date>

            var result = classify(subject, test)
            it("monthly [2016-01, 2017-02, 2017-11]")
            {

                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("yearly")
        {
            subject {
                DateClassifier(mockConfig(YEARLY))
            }
            val (test, correct) = getTestDate(YEARLY)
            correct as List<Date>

            var result = classify(subject, test)
            it("yearly [2016, 2017]")
            {

                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
    }
})