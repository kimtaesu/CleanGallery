package com.hucet.clean.gallery.gallery.sort

import android.content.Context
import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.sort.SortOptions.SORT_TYPE.*
import com.hucet.clean.gallery.gallery.sort.SortOptions.SORT_TYPE.Companion.DATE_TYPES
import com.hucet.clean.gallery.gallery.sort.SortOptions.SORT_TYPE.Companion.DIRECOTRY_TYPE
import com.hucet.clean.gallery.gallery.sort.SortOptions.SORT_TYPE.Companion.MEDIUM_TYPES

/**
 * Created by taesu on 2017-11-12.
 */
class SortOptions(val sort: SORT_TYPE, var orderBy: ORDER_BY = ORDER_BY.DESC) {
    enum class ORDER_BY(val title: Int, val bit: Long) {
        DESC(R.string.sort_option_descending, SORT_DESCENDING), ASC(R.string.sort_option_ascending, SORT_ASCENDING);

        companion object {
            val KEY = ORDER_BY::class.java.name
        }
    }

    enum class SORT_TYPE(val title: Int, val bit: Long) {
        NAME(R.string.sort_option_filename, SORT_BY_NAME),
        SIZE(R.string.sort_option_size, SORT_BY_SIZE),
        TAKEN(R.string.sort_option_date_taken, SORT_BY_DATE_TAKEN),
        PATH(R.string.sort_option_path, SORT_BY_PATH),
        MODIFIED(R.string.sort_option_last_modified, SORT_BY_DATE_MODIFIED),

        COUNT(R.string.sort_option_count, SORT_BY_COUNT),
        DIR_NAME(R.string.sort_option_directory_name, SORT_BY_DIR_NAME),
        DIR_PATH(R.string.sort_option_directory_path, SORT_BY_DIR_PATH),

        DAILY(R.string.sort_option_daily, SORT_BY_DAILY),
        MONTHLY(R.string.sort_option_monthly, SORT_BY_MONTHLY),
        YEARLY(R.string.sort_option_yearly, SORT_BY_YEARLY);

        companion object {
            val KEY = SORT_TYPE::class.java.name
            val DIRECOTRY_TYPE = listOf(COUNT, DIR_PATH, DIR_NAME)
            val MEDIUM_TYPES = listOf(NAME, MODIFIED, TAKEN, PATH, SIZE)
            val DATE_TYPES = listOf(DAILY, MONTHLY, YEARLY)
        }

        fun isDateType(): Boolean {
            return this in DATE_TYPES
        }

        fun isMediumType(): Boolean {
            return this in MEDIUM_TYPES
        }

        fun isDirType(): Boolean {
            return this in DIRECOTRY_TYPE
        }
    }

    fun validate(categoryMode: CategoryMode, isRoot: Boolean) {
        when (categoryMode) {
            CategoryMode.DATE -> {
                if (this.sort !in DATE_TYPES)
                    throw UnsupportedOperationException("${this.sort.name} ${this.orderBy.name}")
            }
            CategoryMode.DIRECTORY -> {
                if (isRoot) {
                    if (this.sort !in DIRECOTRY_TYPE)
                        throw UnsupportedOperationException("${this.sort.name} ${this.orderBy.name}")
                    return
                }

                if (this.sort !in MEDIUM_TYPES)
                    throw UnsupportedOperationException("${this.sort.name} ${this.orderBy.name}")
            }
        }
    }

    infix fun order(orderBY: ORDER_BY): SortOptions {
        this.orderBy = orderBY
        return this
    }

    fun isDesc(): Boolean {
        if (orderBy.bit and SORT_ASCENDING > 0)
            return false
        return true
    }

    fun bit(): Long = sort.bit or orderBy.bit

    companion object {
        fun getFromSortBit(sortBit: Long): SortOptions {
            val sortType = SORT_TYPE.values().first {
                it.bit and sortBit > 0
            }

            val order = ORDER_BY.values().first {
                it.bit and sortBit > 0
            }
            return SortOptions(sortType, order)
        }
    }

    fun getDateGroupString(context: Context): String {
        return when (sort) {
            DAILY -> {
                context.getString(R.string.format_group_daily)
            }
            MONTHLY -> {
                context.getString(R.string.format_group_monthly)
            }

            YEARLY -> {
                context.getString(R.string.format_group_yearly)
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}