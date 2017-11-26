package com.hucet.clean.gallery.gallery.sort

import com.hucet.clean.gallery.gallery.sort.SortOptions.*
import com.hucet.clean.gallery.gallery.sort.SortOptions.SORT_TYPE.*
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */
class SortComparatorFactory {
    companion object {
        fun createComparator(sortOption: SortOptions): Comparator<Medium> {
            val deferredComparator = DefferedComparator()
            val deferComparable = deferredComparator.getMediumComparable(sortOption)
            return Comparator<Medium> { o1, o2 ->
                deferComparable.invoke(o1, o2)
            }
        }

        fun createDateComparator(sortOption: SortOptions): Comparator<String> {
            val deferredComparator = DefferedComparator()
            val deferComparable = deferredComparator.getStringComparable(sortOption)
            return Comparator<String> { o1, o2 ->
                deferComparable.invoke(o1, o2)
            }
        }

        fun createDirComparator(sortOption: SortOptions): Comparator<Directory> {
            val deferredComparator = DefferedComparator()
            val deferComparable = deferredComparator.getDirComparable(sortOption)
            return Comparator<Directory> { o1, o2 ->
                deferComparable.invoke(o1, o2)
            }
        }
    }


    private class DefferedComparator() {
        fun getStringComparable(sortOption: SortOptions): (String, String) -> Int {
            return { o1: String, o2: String ->
                planSortWithOrderString(sortOption.orderBy, o1, o2)
            }
        }

        fun getDirComparable(sortOption: SortOptions): (Directory, Directory) -> Int {
            println("!!!!!!!!! ${sortOption.sort}")
            return when (sortOption.sort) {
                DIR_NAME -> {
                    return { o1: Directory, o2: Directory ->
                        planSortWithOrderString(sortOption.orderBy, o1.name, o2.name)
                    }
                }
                DIR_PATH -> {
                    return { o1: Directory, o2: Directory ->
                        planSortWithOrderString(sortOption.orderBy, o1.path, o2.path)
                    }
                }
                COUNT -> {
                    return { o1: Directory, o2: Directory ->
                        planSortWithOrderInt(sortOption.orderBy, o1.count, o2.count)
                    }
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }

        fun getMediumComparable(sortOption: SortOptions): (Medium, Medium) -> Int {
            return when (sortOption.sort) {
                NAME -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(sortOption.orderBy, o1.name, o2.name)
                    }
                }
                PATH -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(sortOption.orderBy, o1.path, o2.path)
                    }
                }
                SIZE -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(sortOption.orderBy, o1.size, o2.size)
                    }
                }
                TAKEN -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(sortOption.orderBy, o1.taken, o2.taken)
                    }
                }
                DAILY,
                MONTHLY,
                YEARLY,
                MODIFIED -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(sortOption.orderBy, o1.modified, o2.modified)
                    }
                }
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }

        private fun planSortWithOrderLong(order: SortOptions.ORDER_BY, s1: Long, s2: Long): Int =
                when (order) {
                    ORDER_BY.DESC -> {
                        if (s1 > s2) -1
                        else if (s1 < s2) 1
                        else 0
                    }
                    ORDER_BY.ASC -> {
                        if (s1 > s2) 1
                        else if (s1 < s2) -1
                        else 0
                    }
                }

        private fun planSortWithOrderInt(order: SortOptions.ORDER_BY, s1: Int, s2: Int): Int =
                when (order) {
                    ORDER_BY.DESC -> {
                        if (s1 > s2) -1
                        else if (s1 < s2) 1
                        else 0
                    }
                    ORDER_BY.ASC -> {
                        if (s1 > s2) 1
                        else if (s1 < s2) -1
                        else 0
                    }
                }


        private fun planSortWithOrderString(orderBY: SortOptions.ORDER_BY, s1: String, s2: String): Int =
                when (orderBY) {
                    ORDER_BY.DESC -> {
                        if (s1 > s2) -1
                        else if (s1 < s2) 1
                        else 0
                    }
                    ORDER_BY.ASC -> {
                        if (s1 > s2) 1
                        else if (s1 < s2) -1
                        else 0
                    }
                }
    }

}