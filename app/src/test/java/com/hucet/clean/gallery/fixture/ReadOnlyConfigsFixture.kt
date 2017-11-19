package com.hucet.clean.gallery.fixture

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptionTypeTest
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.mock

/**
 * Created by taesu on 2017-11-19.
 */
object ReadOnlyConfigsFixture {
    fun readOnlyConfigs(
            categoryMode: CategoryMode = CategoryMode.DIRECTORY,
            viewModeType: ViewModeType = ViewModeType.LINEAR,
            sortOptionType: SortOptionType = SortOptionType.BY_NAME
    ): ReadOnlyConfigs {
        return ReadOnlyConfigs(categoryMode, viewModeType, sortOptionType)
    }

    fun mockReadOnlyConfigs(
            categoryMode: CategoryMode = CategoryMode.DIRECTORY,
            viewModeType: ViewModeType = ViewModeType.LINEAR,
            sortOptionType: SortOptionType = SortOptionType.BY_NAME
    ): ReadOnlyConfigs {
        val mock = mock<ReadOnlyConfigs>()
        whenever(mock.getCategoryMode()).thenReturn(categoryMode)
        whenever(mock.getViewModeType()).thenReturn(viewModeType)
        whenever(mock.getSortOptionType()).thenReturn(sortOptionType)
        return mock
    }
}