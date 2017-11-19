package com.hucet.clean.gallery.gallery.sort

import android.provider.MediaStore
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.AnyException
import org.amshove.kluent.`should equal to`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.xdescribe

/**
 * Created by taesu on 2017-11-17.
 */

class SortOptionTypeForMediaTest : Spek({
    val desc = "DESC"
    val asc = "ASC"

    describe("sortOption [SORT_BY_NAME or SORT_DESCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_NAME or SORT_DESCENDING).media()
        it("sortString == _display_name DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DISPLAY_NAME} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_NAME or SORT_ASCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_NAME or SORT_ASCENDING).media()
        it("sortString == _display_name ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DISPLAY_NAME} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_MODIFIED or SORT_DESCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_DATE_MODIFIED or SORT_DESCENDING).media()
        it("sortString == date_modified DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_MODIFIED} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_MODIFIED or SORT_ASCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_DATE_MODIFIED or SORT_ASCENDING).media()
        it("sortString == date_modified ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_MODIFIED} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_SIZE or SORT_DESCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_SIZE or SORT_DESCENDING).media()
        it("sortString == _size DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.SIZE} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_SIZE or SORT_ASCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_SIZE or SORT_ASCENDING).media()
        it("sortString == _size ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.SIZE} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_PATH or SORT_DESCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_PATH or SORT_DESCENDING).media()
        it("sortString == _data DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATA} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_PATH or SORT_ASCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_PATH or SORT_ASCENDING).media()
        it("sortString == _data ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATA} ${asc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_TAKEN or SORT_DESCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_DATE_TAKEN or SORT_DESCENDING).media()
        it("sortString == datetaken DESC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_TAKEN} ${desc}"
        }
    }
    describe("sortOption [SORT_BY_DATE_TAKEN or SORT_ASCENDING]")
    {
        val sortString = SortOptionType.get(SORT_BY_DATE_TAKEN or SORT_ASCENDING).media()
        it("sortString == datetaken ASC")
        {
            sortString `should equal to` "${MediaStore.Images.Media.DATE_TAKEN} ${asc}"
        }
    }
})

