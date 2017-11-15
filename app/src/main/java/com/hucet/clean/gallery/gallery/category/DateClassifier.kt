package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.SORT_BY_DAILY
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifier(private val appConfig: ApplicationConfig) : CategoryStrategy<Basic> {
    enum class DATE_SORT_TYPE(private val format: String, private val index: Int) {
        DAILY("yyyy-MM-dd", 1), MONTHLY("yyyy-MM", 2), YEARLY("yyyy", 4);

        fun value(): Int {
            return index
        }

        fun format(): String {
            return format
        }
    }


    private fun getConfigFormat(curPath: String): String {
        return appConfig.getDateSortType(curPath).format()
    }

    override fun classify(items: List<Medium>, curPath: String): List<Basic> {
        val format = SimpleDateFormat(getConfigFormat(curPath))
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