package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.TestData
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.ByOrder.*
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptionType.*
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
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

fun getTestDate(type: SortOptionType): TestData {
    when (type) {
        BY_DAILY -> {
            return MediumFixture.TEST_CATEGORY_DAILY
        }
        BY_MONTHLY -> {
            return MediumFixture.TEST_CATEGORY_MONTHLY
        }
        BY_YEARLY -> {
            return MediumFixture.TEST_CATEGORY_YEARLY
        }
        else -> {
            throw IllegalArgumentException()
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
    val config by memoized { mock<ApplicationConfig>() }

    given("a dateClassifier")
    {
        subject {
            DateClassifier(config)
        }
        on("daily")
        {
            val (test, correct) = getTestDate(BY_DAILY)
            correct as List<Date>

            whenever(config.getDateSortType()).thenReturn(SortOptions(BY_DAILY, BY_DESC))

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
            val (test, correct) = getTestDate(BY_MONTHLY)
            correct as List<Date>

            whenever(config.getDateSortType()).thenReturn(SortOptions(BY_MONTHLY, BY_DESC))

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
            val (test, correct) = getTestDate(BY_YEARLY)
            correct as List<Date>

            whenever(config.getDateSortType()).thenReturn(SortOptions(BY_YEARLY, BY_DESC))

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