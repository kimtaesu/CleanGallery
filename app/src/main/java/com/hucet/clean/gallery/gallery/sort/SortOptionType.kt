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


    infix fun byOrder(sort: Int): ByOrder {
        val order = ByOrder.values().find { sort and it.bitAttr > 0 }
        order ?: throw IllegalArgumentException()
        return order
    }

    infix fun validate(categoryMode: CategoryMode) {
        when (categoryMode) {
            CategoryMode.DATE -> {
                when (this) {
                    BY_DAILY, BY_MONTHLY, BY_YEARLY -> {
                    }
                    else -> throw UnsupportedOperationException(this.option)
                }
            }
            CategoryMode.DIRECTORY -> {
                when (this) {
                    BY_NAME, BY_MODIFIED, BY_TAKEN, BY_PATH, BY_SIZE -> {
                    }
                    else -> throw UnsupportedOperationException(this.option)
                }
            }
        }
    }
}

enum class ByOrder(val option: String, val title: Int, val bitAttr: Int) {
    BY_ASC("ASC", R.string.sort_option_ascending, SORT_ASCENDING),
    BY_DESC("DESC", R.string.sort_option_descending, SORT_DESCENDING)
}

data class SortOptions(val sortOptionType: SortOptionType, val byOrder: ByOrder) {
    fun getDateFormat() = sortOptionType.option

    fun getSortOptionString(): String {
        return "${sortOptionType.option} ${byOrder.option}"
    }
}