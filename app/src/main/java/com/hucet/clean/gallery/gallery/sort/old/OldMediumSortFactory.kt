package com.hucet.clean.gallery.gallery.sort.old

import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-12.
 */
class OldMediumSortFactory {
    companion object {
        fun createComparator(optionsOld: OldMediaSortOptions, order: OldMediaSortOptions.ORDER = OldMediaSortOptions.ORDER.DESC): Comparator<Medium> {
            val deferredComparator = DefferedComparator()
            val op = optionsOld with order
            val deferComparable = deferredComparator.getComparable(op)
            return Comparator<Medium> { o1, o2 ->
                deferComparable.invoke(o1, o2)
            }
        }

    }

    private class DefferedComparator() {
        fun getComparable(optionsOld: OldMediaSortOptions): (Medium, Medium) -> Int {
            when (optionsOld.strategy) {
                OldMediaSortOptions.STRATEGY.NAME -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(optionsOld.order, o1.name, o2.name)
                    }
                }
                OldMediaSortOptions.STRATEGY.PATH -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderString(optionsOld.order, o1.path, o2.path)
                    }
                }
                OldMediaSortOptions.STRATEGY.SIZE -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(optionsOld.order, o1.size, o2.size)
                    }
                }
                OldMediaSortOptions.STRATEGY.TAKEN -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(optionsOld.order, o1.taken, o2.taken)
                    }
                }
                OldMediaSortOptions.STRATEGY.MODIFIED -> {
                    return { o1: Medium, o2: Medium ->
                        planSortWithOrderLong(optionsOld.order, o1.modified, o2.modified)
                    }
                }
            }
        }

        private fun planSortWithOrderLong(order: OldMediaSortOptions.ORDER, s1: Long, s2: Long): Int {
            return when (order) {
                OldMediaSortOptions.ORDER.DESC -> {
                    if (s1 > s2) -1
                    else if (s1 < s2) 1
                    else 0
                }
                OldMediaSortOptions.ORDER.ASC -> {
                    if (s1 > s2) 1
                    else if (s1 < s2) -1
                    else 0
                }
            }
        }

        private fun planSortWithOrderString(order: OldMediaSortOptions.ORDER, s1: String, s2: String): Int {
            return when (order) {
                OldMediaSortOptions.ORDER.DESC -> {
                    if (s1 > s2) -1
                    else if (s1 < s2) 1
                    else 0
                }
                OldMediaSortOptions.ORDER.ASC -> {
                    if (s1 > s2) 1
                    else if (s1 < s2) -1
                    else 0
                }
            }
        }
    }

}