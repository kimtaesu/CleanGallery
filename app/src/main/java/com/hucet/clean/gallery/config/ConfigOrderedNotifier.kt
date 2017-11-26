package com.hucet.clean.gallery.config

import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.gallery.view_mode.ViewModeType

/**
 * Created by taesu on 2017-11-27.
 */

class ConfigOrderedNotifier(
        observers: Set<OnConfigObserver>
) {
    private val HIGH_PRIORITY = listOf(
            ConfigLogic::class
    )
    private val LOW_PRIORITY = listOf(
            MainActivity::class
    )
    private val orderedObservers = LinkedHashSet<OnConfigObserver>()

    init {
        orderedObservers.addAll(
                observers.sortedByDescending {
                    HIGH_PRIORITY.indexOf(it::class)
                }.sortedBy {
                    LOW_PRIORITY.indexOf(it::class)
                }
        )
    }

    fun categoryNotify(categoryMode: CategoryMode) {
        orderedObservers.forEach {
            it.onCategoryChanged(categoryMode)
        }
    }

    fun filterNotify(filterBit: Long) {
        orderedObservers.forEach {
            it.onFilterChanged(filterBit)
        }
    }

    fun viewModeNotify(viewModeType: ViewModeType) {
        orderedObservers.forEach {
            it.onViewModeChanged(viewModeType)
        }
    }

    fun sortOptionNotify(selectedSort: SortOptions) {
        orderedObservers.forEach {
            it.onSortChanged(selectedSort)
        }
    }
}
