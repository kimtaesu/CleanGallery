package com.hucet.clean.gallery.config

import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.view_mode.ViewModeType

/**
 * Created by taesu on 2017-11-27.
 */
class ConfigLogic(
        private val config: ApplicationConfig
) : OnConfigObserver {
    override fun onCategoryChanged(categoryMode: CategoryMode) {
        config.apply {
            this.categoryMode = categoryMode
            if (isCategoryDate()) {
                viewModeType = ViewModeType.GRID
            }
        }
    }

    override fun onFilterChanged(filterBit: Long) {
        config.apply {
            filterdType = filterBit
        }
    }

    override fun onViewModeChanged(viewModeType: ViewModeType) {
        config.apply {
            this.viewModeType = viewModeType
        }
    }

}