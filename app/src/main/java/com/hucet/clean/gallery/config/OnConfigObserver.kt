package com.hucet.clean.gallery.config

import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.view_mode.ViewModeType

/**
 * Created by taesu on 2017-11-27.
 */
interface OnConfigObserver {
    fun onCategoryChanged(categoryMode: CategoryMode)
    fun onFilterChanged(filterBit: Long)
    fun onViewModeChanged(viewModeType: ViewModeType)
}