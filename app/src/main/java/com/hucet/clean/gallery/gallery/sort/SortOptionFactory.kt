package com.hucet.clean.gallery.gallery.sort

import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */
class SortOptionFactory {
    companion object {
        fun createComparator(optionsOld: SortOptions): Comparator<Medium> {
            val deferredComparator = DefferedComparator()
            val deferComparable = deferredComparator.getComparable(optionsOld)
            return Comparator<Medium> { o1, o2 ->
                deferComparable.invoke(o1, o2)
            }
        }
    }

    private class DefferedComparator() {
        fun getComparable(optionsOld: SortOptions): (Medium, Medium) -> Int {
            return when (optionsOld.sort) {
                SortOptions.SORT_TYPE.NAME -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(optionsOld.orderBY, o1.name, o2.name)
                    }
                }
                SortOptions.SORT_TYPE.PATH -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(optionsOld.orderBY, o1.path, o2.path)
                    }
                }
                SortOptions.SORT_TYPE.SIZE -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(optionsOld.orderBY, o1.size, o2.size)
                    }
                }
                SortOptions.SORT_TYPE.TAKEN -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(optionsOld.orderBY, o1.taken, o2.taken)
                    }
                }
                SortOptions.SORT_TYPE.MODIFIED -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(optionsOld.orderBY, o1.modified, o2.modified)
                    }
                }
                SortOptions.SORT_TYPE.DAILY,
                SortOptions.SORT_TYPE.MONTHLY,
                SortOptions.SORT_TYPE.YEARLY -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(optionsOld.orderBY, o1.modified, o2.modified)
                    }
                }
            }
        }

        private fun planSortWithOrderLong(orderBY: SortOptions.ORDER_BY, s1: Long, s2: Long): Int =
                when (orderBY) {
                    SortOptions.ORDER_BY.DESC -> {
                        if (s1 > s2) -1
                        else if (s1 < s2) 1
                        else 0
                    }
                    SortOptions.ORDER_BY.ASC -> {
                        if (s1 > s2) 1
                        else if (s1 < s2) -1
                        else 0
                    }
                }

        private fun planSortWithOrderString(orderBY: SortOptions.ORDER_BY, s1: String, s2: String): Int =
                when (orderBY) {
                    SortOptions.ORDER_BY.DESC -> {
                        if (s1 > s2) -1
                        else if (s1 < s2) 1
                        else 0
                    }
                    SortOptions.ORDER_BY.ASC -> {
                        if (s1 > s2) 1
                        else if (s1 < s2) -1
                        else 0
                    }
                }
    }

}