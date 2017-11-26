package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.view_mode.ViewModeType
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.mock

/**
 * Created by taesu on 2017-11-26.
 */
object ConfigFixture {
    fun mockConfig(
            categoryMode: CategoryMode = CategoryMode.DIRECTORY,
            sortOptions: SortOptions = SortOptions(SortOptions.SORT_TYPE.NAME, SortOptions.ORDER_BY.DESC),
            filterdType: Long = VIDEOS or IMAGES or GIFS,
            viewModeType: ViewModeType = ViewModeType.LINEAR,
            showHidden: Boolean = false
    ): ApplicationConfig {
        val mock = mock<ApplicationConfig>()
        whenever(mock.showHidden).thenReturn(showHidden)
        whenever(mock.categoryMode).thenReturn(categoryMode)
        whenever(mock.filterdType).thenReturn(filterdType)
        whenever(mock.sortOptionType).thenReturn(sortOptions)
        whenever(mock.viewModeType).thenReturn(viewModeType)
        return mock
    }
}