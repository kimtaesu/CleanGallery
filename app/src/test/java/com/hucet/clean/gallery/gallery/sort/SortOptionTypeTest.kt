package com.hucet.clean.gallery.gallery.sort

import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek

/**
 * Created by taesu on 2017-11-17.
 */

fun mockCategoryMode(categoryMode: CategoryMode): CategoryMode {
    return spy(categoryMode)
}

class SortOptionTypeTest : Spek({

    describe("fail to validate in directory")
    {
        val sortType = SortOptionType.BY_PATH
        val func = { sortType.validate(mockCategoryMode(CategoryMode.DATE)) }
        it("should be throw exception")
        {
            func shouldThrow AnyException
        }
    }
    describe("success to validate in directory")
    {
        val sortType = SortOptionType.BY_PATH
        val func = { sortType.validate(mockCategoryMode(CategoryMode.DIRECTORY)) }
        it("should not be throw exception")
        {
            func shouldNotThrow AnyException
        }
    }
    describe("fail to validate in directory")
    {
        val sortType = SortOptionType.BY_DAILY
        val func = { sortType.validate(mockCategoryMode(CategoryMode.DIRECTORY)) }
        it("should be throw exception")
        {
            func shouldThrow AnyException
        }
    }
    describe("success to validate in directory")
    {
        val sortType = SortOptionType.BY_DAILY
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
            true `should be` SortOptionType.isDirecotryType(SortOptionType.BY_MODIFIED)
            true `should be` SortOptionType.isDirecotryType(SortOptionType.BY_NAME)
            true `should be` SortOptionType.isDirecotryType(SortOptionType.BY_SIZE)
            true `should be` SortOptionType.isDirecotryType(SortOptionType.BY_TAKEN)
            true `should be` SortOptionType.isDirecotryType(SortOptionType.BY_PATH)

            false `should be` SortOptionType.isDirecotryType(SortOptionType.BY_MONTHLY)
            false `should be` SortOptionType.isDirecotryType(SortOptionType.BY_DAILY)
            false `should be` SortOptionType.isDirecotryType(SortOptionType.BY_YEARLY)
        }
        it("date type 검증")
        {
            true `should be` SortOptionType.isDateType(SortOptionType.BY_MONTHLY)
            true `should be` SortOptionType.isDateType(SortOptionType.BY_DAILY)
            true `should be` SortOptionType.isDateType(SortOptionType.BY_YEARLY)

            false `should be` SortOptionType.isDateType(SortOptionType.BY_MODIFIED)
            false `should be` SortOptionType.isDateType(SortOptionType.BY_NAME)
            false `should be` SortOptionType.isDateType(SortOptionType.BY_SIZE)
            false `should be` SortOptionType.isDateType(SortOptionType.BY_TAKEN)
            false `should be` SortOptionType.isDateType(SortOptionType.BY_PATH)
        }
    }
})