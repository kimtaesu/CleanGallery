package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.fixture.MediumFixture
import com.hucet.clean.gallery.fixture.SortMediumFixture
import com.hucet.clean.gallery.fixture.TestData
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.ByOrder.*
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptionType.*
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
            return if (type.isDesc())
                SortMediumFixture.TEST_CATEGORY_DAILY_DESC
            else
                SortMediumFixture.TEST_CATEGORY_DAILY_ASC
        }
        BY_MONTHLY -> {
            return if (type.isDesc())
                SortMediumFixture.TEST_CATEGORY_MONTHLY_DESC
            else
                SortMediumFixture.TEST_CATEGORY_MONTHLY_ASC
        }
        BY_YEARLY -> {
            return if (type.isDesc())
                SortMediumFixture.TEST_CATEGORY_YEARLY_DESC
            else
                SortMediumFixture.TEST_CATEGORY_YEARLY_ASC
        }
        else -> {
            throw IllegalArgumentException()
        }
    }
}

fun classify(d: DateClassifier, sortType: SortOptionType, test: List<Medium>): List<Date> {
    var result = d.classify(sortType, test)
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
            DateClassifier()
        }
        on("desc daily")
        {
            val (test, correct) = getTestDate(BY_DAILY order BY_DESC)
            correct as List<Date>

            val sortType = SortOptionType.BY_DAILY order ByOrder.BY_DESC
            var result = classify(subject, sortType, test)

            it("daily [2017-11-06, 2017-02-23, 2016-01-01]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("asc daily")
        {
            val (test, correct) = getTestDate(BY_DAILY order BY_ASC)
            correct as List<Date>

            val sortType = SortOptionType.BY_DAILY order ByOrder.BY_ASC
            var result = classify(subject, sortType, test)

            it("daily [2017-11-06, 2017-02-23, 2016-01-01]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("desc monthly")
        {
            val (test, correct) = getTestDate(BY_MONTHLY order BY_DESC)
            correct as List<Date>

            val sortType = SortOptionType.BY_MONTHLY order ByOrder.BY_DESC
            var result = classify(subject, sortType, test)
            it("monthly [2017-11, 2017-02, 2016-01]")
            {

                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("asc monthly")
        {
            val (test, correct) = getTestDate(BY_MONTHLY order BY_ASC)
            correct as List<Date>

            val sortType = SortOptionType.BY_MONTHLY order ByOrder.BY_ASC
            var result = classify(subject, sortType, test)
            it("monthly [2017-11, 2017-02, 2016-01]")
            {

                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("desc yearly")
        {
            val (test, correct) = getTestDate(BY_YEARLY order BY_DESC)
            correct as List<Date>

            val sortType = SortOptionType.BY_YEARLY order ByOrder.BY_DESC
            var result = classify(subject, sortType, test)
            it("yearly [2017, 2016]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("asc yearly")
        {
            val (test, correct) = getTestDate(BY_YEARLY order BY_ASC)
            correct as List<Date>

            val sortType = SortOptionType.BY_YEARLY order BY_ASC
            var result = classify(subject, sortType, test)
            it("yearly [2016, 2017]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
    }
})