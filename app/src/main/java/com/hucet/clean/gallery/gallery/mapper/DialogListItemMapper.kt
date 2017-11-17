package com.hucet.clean.gallery.gallery.mapper

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.DialogItem
import java.lang.UnsupportedOperationException

/**
 * Created by taesu on 2017-11-17.
 */
class DialogListItemMapper {
    private inline fun <reified T> mapOnCheckItem(context: Context, items: List<T>, checkedItem: SortOptions): Pair<String, List<DialogItem>> {
        return when (T::class) {
            SortOptionType::class -> {
                items as List<SortOptionType>
                return SortOptionType::class.java.name to items
                        .mapIndexed { index, sortOptionType ->
                            DialogItem(index,
                                    context.getString(sortOptionType.title),
                                    checkedItem.sortOptionType == sortOptionType,
                                    sortOptionType.bitAttr)
                        }
            }
            ByOrder::class -> {
                items as List<ByOrder>
                return ByOrder::class.java.name to items
                        .mapIndexed { index, byOrder ->
                            DialogItem(index,
                                    context.getString(byOrder.title),
                                    checkedItem.byOrder == byOrder,
                                    byOrder.bitAttr)
                        }
            }
            else -> {
                throw UnsupportedOperationException()
            }
        }

    }

    fun sortItemsMap(context: Context, config: ApplicationConfig): Map<String, List<DialogItem>> {
        return when (config.categoryMode) {
            CategoryMode.DATE -> {
                mapOf(mapOnCheckItem(context, SortOptionType.DATE_TYPES, config.getDateSortType()),
                        mapOnCheckItem(context, ByOrder.values().toList(), config.getDateSortType()))
            }
            CategoryMode.DIRECTORY -> {
                mapOf(mapOnCheckItem(context, SortOptionType.DIRECTORY_TYPES, config.getDateSortType()),
                        mapOnCheckItem(context, ByOrder.values().toList(), config.getDateSortType()))
            }
        }
    }
}