package com.hucet.clean.gallery.gallery.category

import android.content.Context
import com.hucet.clean.gallery.fixture.SortMediumFixture
import com.hucet.clean.gallery.fixture.TestData
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.`should equal to`
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import java.text.SimpleDateFormat

/**
 * Created by taesu on 2017-11-10.
 */

fun classify(d: TestDateTransformer, sortType: SortOptions, test: List<Medium>): List<Date> {
    var result = d.transform(sortType, test)
    return result.filter {
        it.viewType == GalleryType.DATE
    }.map {
        it as Date
    }
}

class DateClassifierTest : SubjectSpek<TestDateTransformer>({
    given("a dateClassifier")
    {
        subject {
            TestDateTransformer(mock())
        }
        on("desc daily")
        {
            val sortOption = SortOptions(SortOptions.SORT_TYPE.DAILY)

            val (test, correct) = getTestDate(sortOption)
            correct as List<Date>

            var result = classify(subject, sortOption, test)

            it("daily [2017-11-06, 2017-02-23, 2016-01-01]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("asc daily")
        {
            val sortOption = SortOptions(SortOptions.SORT_TYPE.DAILY)
            val (test, correct) = getTestDate(sortOption)
            correct as List<Date>

            var result = classify(subject, sortOption, test)

            it("daily [2017-11-06, 2017-02-23, 2016-01-01]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("desc monthly")
        {
            val sortOption = SortOptions(SortOptions.SORT_TYPE.MONTHLY)
            val (test, correct) = getTestDate(sortOption)
            correct as List<Date>

            var result = classify(subject, sortOption, test)
            it("monthly [2017-11, 2017-02, 2016-01]")
            {

                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("asc monthly")
        {
            val sortOption = SortOptions(SortOptions.SORT_TYPE.MONTHLY)
            val (test, correct) = getTestDate(sortOption)
            correct as List<Date>

            var result = classify(subject, sortOption, test)
            it("monthly [2017-11, 2017-02, 2016-01]")
            {

                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("desc yearly")
        {
            val sortOption = SortOptions(SortOptions.SORT_TYPE.YEARLY)
            val (test, correct) = getTestDate(sortOption)
            correct as List<Date>

            var result = classify(subject, sortOption, test)
            it("yearly [2017, 2016]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
        on("asc yearly")
        {
            val sortOption = SortOptions(SortOptions.SORT_TYPE.YEARLY)
            val (test, correct) = getTestDate(sortOption)
            correct as List<Date>

            var result = classify(subject, sortOption, test)
            it("yearly [2016, 2017]")
            {
                result.forEachIndexed { index, it ->
                    it.date `should equal to` correct[index].date
                }
            }
        }
    }
})

class TestDateTransformer(context: Context) : DateTransformer(context) {
    override fun getFormat(sortOptionType: SortOptions): SimpleDateFormat {
        val dateString = when (sortOptionType.sort) {
            SortOptions.SORT_TYPE.DAILY -> {
                "yyyy-MM-dd"
            }
            SortOptions.SORT_TYPE.MONTHLY -> {
                "yyyy-MM"
            }
            SortOptions.SORT_TYPE.YEARLY -> {
                "yyyy"
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
        return SimpleDateFormat(dateString)
    }
}

fun getTestDate(type: SortOptions): TestData {
    when (type.sort) {
        SortOptions.SORT_TYPE.DAILY -> {
            return if (type.isDesc())
                SortMediumFixture.TEST_CATEGORY_DAILY_DESC
            else
                SortMediumFixture.TEST_CATEGORY_DAILY_ASC
        }
        SortOptions.SORT_TYPE.MONTHLY -> {
            return if (type.isDesc())
                SortMediumFixture.TEST_CATEGORY_MONTHLY_DESC
            else
                SortMediumFixture.TEST_CATEGORY_MONTHLY_ASC
        }
        SortOptions.SORT_TYPE.YEARLY -> {
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
