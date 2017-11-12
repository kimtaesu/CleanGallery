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
class DateClassifier(val appConfig: ApplicationConfig) : CategoryStrategy<Basic> {
    enum class DATE_SORT_TYPE(private val format: String, private val index: Int) {
        DAILY("yyyy-MM-dd", 1), MONTHLY("yyyy-MM", 2), YEARLY("yyyy", 3);

        fun value(): Int {
            return index
        }

        fun format(): String {
            return format
        }
    }


    private fun getConfigFormat(): String {
        return when (appConfig.dateSortType) {
            DATE_SORT_TYPE.DAILY.value() -> {
                DATE_SORT_TYPE.DAILY.format()
            }
            DATE_SORT_TYPE.MONTHLY.value() -> {
                DATE_SORT_TYPE.MONTHLY.format()
            }
            DATE_SORT_TYPE.YEARLY.value() -> {
                DATE_SORT_TYPE.YEARLY.format()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    override fun category(items: List<Medium>): List<Basic> {
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