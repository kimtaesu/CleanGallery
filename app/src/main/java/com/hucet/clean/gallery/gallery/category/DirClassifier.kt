package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.sort.SortComparatorFactory
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import java.io.File

/**
 * Created by taesu on 2017-11-10.
 */
class DirClassifier : CategoryStrategy<Directory> {
    override fun classify(sortOptionType: SortOptions, items: List<Medium>): List<Directory> {
        return items
                .groupBy {
                    File(it.path)?.parentFile.path
                }
                .map {
                    val first = it.value.first()
                    val dirFile = File(it.key)
                    Directory(
                            count = it.value.size,
                            thumbnail = first.path,
                            path = dirFile.path,
                            name = dirFile.name,
                            id = first.id,
                            viewType = GalleryType.DIRECTORY
                    )
                }
                .sortedWith(SortComparatorFactory.createDirComparator(sortOptionType))
    }
}