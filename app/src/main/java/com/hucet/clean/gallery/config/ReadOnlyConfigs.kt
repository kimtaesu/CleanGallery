package com.hucet.clean.gallery.config

import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.gallery.sort.SortOptionType

/**
 * Created by taesu on 2017-11-19.
 */
class ReadOnlyConfigs(private val c: CategoryMode, private val v: ViewModeType, private val s: SortOptionType,
                      private val f: Int, private val varShowHidden: Boolean) {
    fun getCategoryMode(): CategoryMode {
        return c
    }

    fun getViewModeType(): ViewModeType {
        return v
    }

    fun getSortOptionType(): SortOptionType {
        return s
    }

    fun getFilterBit(): Int = f

    val showHidden = varShowHidden

}