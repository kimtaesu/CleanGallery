package com.hucet.clean.gallery.gallery.category

import com.google.gson.Gson
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Date
import com.hucet.clean.gallery.model.Medium
import java.text.SimpleDateFormat

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifier : CategoryStrategy<Basic> {

    val format: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd")
    }

    override fun category(items: List<Medium>): List<Basic> {
        return items.groupBy {
            format.format(it.modified)
        }
                .flatMap {
                    val temp = ArrayList<Basic>()
                    val date = Date(it.key)
                    temp.add(date)
                    temp.addAll(it.value)
                    temp
                }
    }

}