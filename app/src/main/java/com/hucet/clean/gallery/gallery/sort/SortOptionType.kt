package com.hucet.clean.gallery.gallery.sort

import android.provider.MediaStore
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.category.CategoryMode

/**
 * Created by taesu on 2017-11-17.
 */
enum class SortOptionType(val option: String, val title: Int, val bitAttr: Int) {
    BY_NAME(MediaStore.Images.Media.DISPLAY_NAME, R.string.sort_option_filename, SORT_BY_NAME),
    BY_MODIFIED(MediaStore.Images.Media.DATE_MODIFIED, R.string.sort_option_last_modified, SORT_BY_DATE_MODIFIED),
    BY_TAKEN(MediaStore.Images.Media.DATE_TAKEN, R.string.sort_option_date_taken, SORT_BY_DATE_TAKEN),
    BY_PATH(MediaStore.Images.Media.DATA, R.string.sort_option_path, SORT_BY_PATH),
    BY_SIZE(MediaStore.Images.Media.SIZE, R.string.sort_option_size, SORT_BY_SIZE),

    BY_DAILY("yyyy-MM-dd", R.string.sort_option_daily, SORT_BY_DAILY),
    BY_MONTHLY("yyyy-MM", R.string.sort_option_monthly, SORT_BY_MONTHLY),
    BY_YEARLY("yyyy", R.string.sort_option_yearly, SORT_BY_YEARLY);

    private var orderBy: ByOrder = ByOrder.BY_DESC

    fun media(categoryMode: CategoryMode): String {
// TODO TEst code
        if (categoryMode == CategoryMode.DATE)
            return "${BY_MODIFIED.option} ${orderBy.option}"
        return "${this.option} ${orderBy.option}"
    }

    fun bitWithOrder(): Int {
        return this.bitAttr + orderBy.bitAttr
    }

    fun getOrder(): ByOrder {
        return orderBy
    }

    infix fun order(bit: Int): SortOptionType {
        orderBy = ByOrder.values().first {
            it.bitAttr and bit > 0
        }
        return this
    }

    infix fun order(order: ByOrder): SortOptionType {
        orderBy = order
        return this
    }

    fun isDesc(): Boolean {
        if (orderBy.bitAttr and SORT_ASCENDING > 0)
            return false
        return true
    }

    companion object {
        val KEY = SortOptionType::class.java.name
        val DIRECTORY_TYPES = listOf(BY_NAME, BY_MODIFIED, BY_TAKEN, BY_PATH, BY_SIZE)
        val DATE_TYPES = listOf(BY_DAILY, BY_MONTHLY, BY_YEARLY)
        fun isDirecotryType(sortType: SortOptionType): Boolean {
            return sortType in DIRECTORY_TYPES
        }

        fun isDateType(sortType: SortOptionType): Boolean {
            return sortType in DATE_TYPES
        }

        fun getFromSortOrderBit(sortBit: Int): SortOptionType {
            val sortType = SortOptionType.values().first {
                it.bitAttr and sortBit > 0
            }
            return sortType order sortBit
        }
    }

    infix fun validate(categoryMode: CategoryMode) {
        when (categoryMode) {
            CategoryMode.DATE -> {
                if (this !in DATE_TYPES)
                    throw UnsupportedOperationException(this.option)
            }
            CategoryMode.DIRECTORY -> {
                if (this !in DIRECTORY_TYPES)
                    throw UnsupportedOperationException(this.option)
            }
        }
    }
}

enum class ByOrder(val option: String, val title: Int, val bitAttr: Int) {
    BY_ASC("ASC", R.string.sort_option_ascending, SORT_ASCENDING),
    BY_DESC("DESC", R.string.sort_option_descending, SORT_DESCENDING);

    companion object {
        val KEY = ByOrder::class.java.name
    }
}