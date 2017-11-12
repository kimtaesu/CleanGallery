package com.hucet.clean.gallery.gallery.sort

import android.provider.MediaStore
import com.hucet.clean.gallery.config.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.core.Is.*
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by taesu on 2017-11-12.
 */
class MediaSortOptionsTest {
    private val desc = "DESC"
    private val asc = "ASC"
    @Test
    fun `DisplayName DESC Option 검증`() {
        val sortString = MediaSortOptions.getSortOptions("", mockConfig(SORT_BY_NAME or SORT_DESCENDING), true)
        println(sortString)
        assertThat(sortString, `is`("${MediaStore.Images.Media.DISPLAY_NAME} ${desc}"))
    }

    @Test
    fun `DisplayName ASC Option 검증`() {
        val sortString = MediaSortOptions.getSortOptions("", mockConfig(SORT_BY_NAME or SORT_ASCENDING), true)
        println(sortString)
        assertThat(sortString, `is`("${MediaStore.Images.Media.DISPLAY_NAME} ${asc}"))
    }

    @Test
    fun `DATE_MODIFIED DESC Option 검증`() {
        val sortString = MediaSortOptions.getSortOptions("", mockConfig(SORT_BY_DATE_MODIFIED or SORT_DESCENDING), true)
        println(sortString)
        assertThat(sortString, `is`("${MediaStore.Images.Media.DATE_MODIFIED} ${desc}"))
    }

    @Test
    fun `DATE_MODIFIED ASC Option 검증`() {
        val sortString = MediaSortOptions.getSortOptions("", mockConfig(SORT_BY_DATE_MODIFIED or SORT_ASCENDING), true)
        println(sortString)
        assertThat(sortString, `is`("${MediaStore.Images.Media.DATE_MODIFIED} ${asc}"))
    }

    private fun mockConfig(options: Int): ApplicationConfig {
        val config = mock<ApplicationConfig>()
        return config.apply {
            whenever(getDirSortType(any())).thenReturn(options)
        }
    }
}