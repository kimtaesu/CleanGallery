package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import java.io.File

/**
 * Created by taesu on 2017-11-10.
 */
class DirClassifier : CategoryStrategy {
    override fun <T : Medium> category(items: List<Medium>): Map<String, List<T>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
//    override fun <T : Medium> category(items: List<Medium>): Map<String, List<T>> {
//        items.groupBy {
//            File(it.path)?.parentFile.absolutePath
//        }.map {
//        }
//    }
//    override fun category(items: List<Medium>): List<Directory>> {
//        return items.groupBy {
//            File(it.path)?.parentFile.absolutePath
//        }
//    }
}