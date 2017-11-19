package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.filter.FilterType
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.DialogRadioItem

/**
 * Created by taesu on 2017-11-20.
 */
interface DialogRadioItemMapper {
    fun map(context: Context, readOnlyConfigs: ReadOnlyConfigs): Map<String, List<DialogRadioItem>>

    class FilterMapper() : DialogRadioItemMapper {
        override fun map(context: Context, readOnlyConfigs: ReadOnlyConfigs): Map<String, List<DialogRadioItem>> {
            return mapOf(
                    FilterType.KEY to
                            FilterType.values().mapIndexed { index, filterType ->
                                DialogRadioItem(index, context.getString(filterType.stringId),
                                        filterType.isChecked(readOnlyConfigs.getFilterBit()), filterType.bitAtt)
                            }
            )
        }
    }

    class SortMapper() : DialogRadioItemMapper {
        private fun createDialogRadioItems(context: Context, targetItems: List<SortOptionType>, checkedSortType: SortOptionType): Map<String, List<DialogRadioItem>> {
            val sortItems = targetItems
                    .mapIndexed { index, sortType ->
                        DialogRadioItem(
                                index,
                                context.getString(sortType.title),
                                checkedSortType.bitAttr and sortType.bitAttr > 0,
                                sortType.bitAttr
                        )
                    }
            val orderItems = ByOrder.values()
                    .mapIndexed { index, byOrder ->
                        DialogRadioItem(
                                index,
                                context.getString(byOrder.title),
                                checkedSortType.getOrder().bitAttr and byOrder.bitAttr > 0,
                                byOrder.bitAttr
                        )
                    }

            return mapOf(
                    SortOptionType.KEY to sortItems,
                    ByOrder.KEY to orderItems
            )
        }

        override fun map(context: Context, readOnlyConfigs: ReadOnlyConfigs): Map<String, List<DialogRadioItem>> {
            val checkedSortType = readOnlyConfigs.getSortOptionType()
            when (readOnlyConfigs.getCategoryMode()) {
                CategoryMode.DATE -> {
                    return createDialogRadioItems(context, SortOptionType.DATE_TYPES, checkedSortType)
                }
                CategoryMode.DIRECTORY -> {
                    return createDialogRadioItems(context, SortOptionType.DIRECTORY_TYPES, checkedSortType)
                }
            }
        }

    }
}