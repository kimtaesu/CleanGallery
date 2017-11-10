package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import java.io.File
import java.util.*

/**
 * Created by taesu on 2017-11-10.
 */
class DirClassifier : CategoryStrategy<Directory> {
    override fun category(items: List<Medium>): List<Directory> {
        return items.groupBy {
            File(it.path)?.parentFile.path
        }.map {
            val first = it.value.first()
            val dirFile = File(it.key)
            Directory(
                    count = it.value.size,
                    thumbnail = first.path,
                    path = dirFile.path,
                    name = dirFile.name,
                    id = first.id,
                    viewType = GalleryAdapter.GalleryType.DIRECTORY
            )
        }
    }
}