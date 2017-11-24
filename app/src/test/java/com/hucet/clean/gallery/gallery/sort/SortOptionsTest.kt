package com.hucet.clean.gallery.gallery.sort

import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.SortOptions.SORT_TYPE.*
import com.nhaarman.mockito_kotlin.spy
import org.amshove.kluent.AnyException
import org.amshove.kluent.`should be`
import org.amshove.kluent.shouldNotThrow
import org.amshove.kluent.shouldThrow
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * Created by taesu on 2017-11-25.
 */

fun mockCategoryMode(categoryMode: CategoryMode): CategoryMode {
    return spy(categoryMode)
}

class OldMediaSortOptionsTest : Spek({
    describe("fail to validate in directory")
    {
        val sortType = SortOptions(PATH)
        val func = { sortType validate (mockCategoryMode(CategoryMode.DATE)) }

        it("should be throw exception")
        {
            func shouldThrow AnyException
        }
    }
    describe("success to validate in directory")
    {
        val sortType = SortOptions(PATH)
        val func = { sortType validate (mockCategoryMode(CategoryMode.DIRECTORY)) }
        it("should not be throw exception")
        {
            func shouldNotThrow AnyException
        }
    }
    describe("fail to validate in directory")
    {
        val sortType = SortOptions(DAILY)
        val func = { sortType.validate(mockCategoryMode(CategoryMode.DIRECTORY)) }
        it("should be throw exception")
        {
            func shouldThrow AnyException
        }
    }
    describe("success to validate in directory")
    {
        val sortType = SortOptions(DAILY)
        val func = { sortType.validate(mockCategoryMode(CategoryMode.DATE)) }
        it("should not be throw exception")
        {
            func shouldNotThrow AnyException
        }
    }

    describe("date, directory 분류 검증")
    {
        it("directory type 검증")
        {
            true `should be` MODIFIED.isDirecotryType()
            true `should be` NAME.isDirecotryType()
            true `should be` SIZE.isDirecotryType()
            true `should be` TAKEN.isDirecotryType()
            true `should be` PATH.isDirecotryType()

            false `should be` MONTHLY.isDirecotryType()
            false `should be` DAILY.isDirecotryType()
            false `should be` YEARLY.isDirecotryType()
        }
        it("date type 검증")
        {
            true `should be` MONTHLY.isDateType()
            true `should be` DAILY.isDateType()
            true `should be` YEARLY.isDateType()

            false `should be` MODIFIED.isDateType()
            false `should be` NAME.isDateType()
            false `should be` SIZE.isDateType()
            false `should be` TAKEN.isDateType()
            false `should be` PATH.isDateType()
        }
    }

})

