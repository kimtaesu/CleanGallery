package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifier : CategoryStrategy<Basic> {
    override fun classify(sortOptionType: SortOptionType, items: List<Medium>): List<Basic> {
        val format = SimpleDateFormat(sortOptionType.option)
        return items
                .groupBy {
                    format.format(it.modified)
                }
                .toSortedMap(createComparetor(sortOptionType))
                .flatMap {
                    val temp = ArrayList<Basic>()
                    val date = Date(date = it.key, id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE)
                    temp.add(date)
                    temp.addAll(it.value)
                    temp
                }
    }

    private fun createComparetor(sortOptionType: SortOptionType): Comparator<in String> {
        if (sortOptionType.isDesc()) {
            return Comparator { o1: String, o2: String ->
                if (o1 < o2) 1
                else -1
            }
        } else {
            return Comparator { o1: String, o2: String ->
                if (o1 < o2) -1
                else 1
            }
        }
    }

}