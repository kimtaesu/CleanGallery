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
        println("!!!!!!!! ConfigLogic")
        config.apply {
            this.categoryMode = categoryMode
            if (isCategoryDate()) {
                viewModeType = ViewModeType.GRID
            }
        }
        println("!!!!!!!! ConfigLogic ${config.categoryMode}")
    }

    override fun onFilterChanged(filterBit: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onViewModeChanged(viewModeType: ViewModeType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}