package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifier(private val appConfig: ApplicationConfig) : CategoryStrategy<Basic> {
    private fun getConfigFormat(): String {
        return appConfig.getDateSortType().getDateFormat()
    }

    override fun classify(items: List<Medium>): List<Basic> {
        val format = SimpleDateFormat(getConfigFormat())
        return items.groupBy {
            format.format(it.modified)
        }
                .flatMap {
                    val temp = ArrayList<Basic>()
                    val date = Date(date = it.key, id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE)
                    temp.add(date)
                    temp.addAll(it.value)
                    temp
                }
    }

}