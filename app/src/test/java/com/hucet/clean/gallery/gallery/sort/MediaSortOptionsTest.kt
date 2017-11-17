package com.hucet.clean.gallery.gallery.sort

import android.provider.MediaStore
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.ByOrder.*
import com.hucet.clean.gallery.gallery.sort.SortOptionType.*
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.`should equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * Created by taesu on 2017-11-12.
 */


class MediaSortOptionsTest : Spek({
    val desc = "DESC"
    val asc = "ASC"

    describe("sortOption [SORT_BY_NAME or SORT_DESCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_NAME, BY_DESC))
        it("sortString == _display_name DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DISPLAY_NAME} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_NAME or SORT_ASCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_NAME, BY_ASC))
        it("sortString == _display_name ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DISPLAY_NAME} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_MODIFIED or SORT_DESCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_MODIFIED, BY_DESC))
        it("sortString == date_modified DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_MODIFIED} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_MODIFIED or SORT_ASCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_MODIFIED, BY_ASC))
        it("sortString == date_modified ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_MODIFIED} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_SIZE or SORT_DESCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_SIZE, BY_DESC))
        it("sortString == _size DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.SIZE} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_SIZE or SORT_ASCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_SIZE, BY_ASC))
        it("sortString == _size ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.SIZE} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_PATH or SORT_DESCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_PATH, BY_DESC))
        it("sortString == _data DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATA} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_PATH or SORT_ASCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_PATH, BY_ASC))
        it("sortString == _data ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATA} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_TAKEN or SORT_DESCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_TAKEN, BY_DESC))
        it("sortString == datetaken DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_TAKEN} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_TAKEN or SORT_ASCENDING]")
    {
        val sortString = getSortString(SortOptions(BY_TAKEN, BY_ASC))
        it("sortString == datetaken ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_TAKEN} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_MODIFIED or SORT_ASCENDING] with CategoryMode")
    {
        val sortString = getSortString(SortOptions(BY_TAKEN, BY_ASC), CategoryMode.DATE)
        it("sortString == date_modified DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_MODIFIED} ${desc}"
        }
    }
})

fun getSortString(sortOption: SortOptions, categoryMode: CategoryMode = CategoryMode.DIRECTORY): String {
    return MediaSortOptions.getSortOptions(mockConfig(sortOption, categoryMode))
}

fun mockConfig(options: SortOptions, mode: CategoryMode = CategoryMode.DIRECTORY): ApplicationConfig {
    val config = mock<ApplicationConfig>()
    return config.apply {
        whenever(categoryMode).thenReturn(mode)
        whenever(getDirSortType()).thenReturn(options)
    }
}