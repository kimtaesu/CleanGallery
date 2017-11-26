package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.DialogRadioItem

/**
 * Created by taesu on 2017-11-27.
 */
class DialogSortMapper() {
    private fun createDialogRadioItems(context: Context, targetItems: List<SortOptions.SORT_TYPE>, checkedSortType: SortOptions): Map<String, List<DialogRadioItem>> {
        val sortItems = targetItems
                .mapIndexed { index, sortType ->
                    DialogRadioItem(
                            index,
                            context.getString(sortType.title),
                            checkedSortType.sort.bit and sortType.bit > 0,
                            sortType.bit
                    )
                }
        val orderItems = SortOptions.ORDER_BY.values()
                .mapIndexed { index, byOrder ->
                    DialogRadioItem(
                            index,
                            context.getString(byOrder.title),
                            checkedSortType.orderBy.bit and byOrder.bit > 0,
                            byOrder.bit
                    )
                }

        return mapOf(
                SortOptions.SORT_TYPE.KEY to sortItems,
                SortOptions.ORDER_BY.KEY to orderItems
        )
    }

    fun map(context: Context, categoryMode: CategoryMode, sortOptions: SortOptions, isRoot: Boolean): Map<String, List<DialogRadioItem>> {
        when (categoryMode) {
            CategoryMode.DATE -> {
                return createDialogRadioItems(context, SortOptions.SORT_TYPE.DATE_TYPES, sortOptions)
            }
            CategoryMode.DIRECTORY -> {
                return if (isRoot)
                    createDialogRadioItems(context, SortOptions.SORT_TYPE.DIRECOTRY_TYPE, sortOptions)
                else
                    createDialogRadioItems(context, SortOptions.SORT_TYPE.MEDIUM_TYPES, sortOptions)
            }
        }
    }
}