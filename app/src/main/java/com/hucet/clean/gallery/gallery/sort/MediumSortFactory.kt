package com.hucet.clean.gallery.gallery.sort

import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */
class MediumSortFactory {
    companion object {
        fun createComparator(options: MediaSortOptions, order: MediaSortOptions.ORDER = MediaSortOptions.ORDER.DESC): Comparator<Medium> {
            val deferredComparator = DefferedComparator()
            val op = options with order
            val deferComparable = deferredComparator.getComparable(op)
            return Comparator<Medium> { o1, o2 ->
                deferComparable.invoke(o1, o2)
            }
        }

    }

    private class DefferedComparator() {
        fun getComparable(options: MediaSortOptions): (Medium, Medium) -> Int {
            when (options.strategy) {
                MediaSortOptions.STRATEGY.NAME -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(options.order, o1.name, o2.name)
                    }
                }
                MediaSortOptions.STRATEGY.PATH -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(options.order, o1.path, o2.path)
                    }
                }
                MediaSortOptions.STRATEGY.SIZE -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(options.order, o1.size, o2.size)
                    }
                }
                MediaSortOptions.STRATEGY.TAKEN -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(options.order, o1.taken, o2.taken)
                    }
                }
                MediaSortOptions.STRATEGY.MODIFIED -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(options.order, o1.modified, o2.modified)
                    }
                }
            }
        }

        private fun planSortWithOrderLong(order: MediaSortOptions.ORDER, s1: Long, s2: Long): Int {
            return when (order) {
                MediaSortOptions.ORDER.DESC -> {
                    if (s1 > s2) -1
                    else if (s1 < s2) 1
                    else 0
                }
                MediaSortOptions.ORDER.ASC -> {
                    if (s1 > s2) 1
                    else if (s1 < s2) -1
                    else 0
                }
            }
        }

        private fun planSortWithOrderString(order: MediaSortOptions.ORDER, s1: String, s2: String): Int {
            return when (order) {
                MediaSortOptions.ORDER.DESC -> {
                    if (s1 > s2) -1
                    else if (s1 < s2) 1
                    else 0
                }
                MediaSortOptions.ORDER.ASC -> {
                    if (s1 > s2) 1
                    else if (s1 < s2) -1
                    else 0
                }
            }
        }
    }

}