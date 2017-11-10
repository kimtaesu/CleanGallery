package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.model.Medium
import java.text.SimpleDateFormat

/**
 * Created by taesu on 2017-11-10.
 */
class DateClassifier : CategoryStrategy {

    val format: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd")
    }

    //    override fun category(items: List<Medium>): Map<String, List<Medium>> {
//        return items.groupBy {
//            format.format(it.modified)
//        }
//    }
    override fun <T : Medium> category(items: List<Medium>): Map<String, List<T>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}