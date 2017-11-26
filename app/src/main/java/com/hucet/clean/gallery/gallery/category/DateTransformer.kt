package com.hucet.clean.gallery.gallery.category

import android.content.Context
import com.hucet.clean.gallery.gallery.sort.SortComparatorFactory
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by taesu on 2017-11-10.
 */
open class DateTransformer(private val context: Context) : CategoryStrategy<Basic> {
    override fun transform(sortOptionType: SortOptions, items: List<Medium>): List<Basic> {
        val format = getFormat(sortOptionType)
        return items
                .groupBy {
                    format.format(it.modified)
                }
                .toSortedMap(SortComparatorFactory.createDateComparator(sortOptionType))
                .flatMap {
                    val temp = ArrayList<Basic>()
                    val date = Date(date = it.key, id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE)
                    temp.add(date)
                    temp.addAll(it.value)
                    temp
                }
    }


    open fun getFormat(sortOptionType: SortOptions): SimpleDateFormat =
            SimpleDateFormat(sortOptionType.getDateGroupString(context))
}