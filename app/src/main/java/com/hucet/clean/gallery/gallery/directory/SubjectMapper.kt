package com.hucet.clean.gallery.gallery.directory

import com.hucet.clean.gallery.gallery.category.DateTransformer
import com.hucet.clean.gallery.gallery.category.DirTransformer
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.hucet.clean.gallery.gallery.sort.SortComparatorFactory
import com.hucet.clean.gallery.gallery.sort.SortOptions
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-26.
 */

sealed class SubjectMapper<T, R> {
    open fun map(items: List<T>, curPath: () -> String): List<T> = items
    open fun filter(items: List<T>, filter: (Medium) -> Boolean): List<T> = items
    open fun sort(items: List<T>, sortOption: () -> SortOptions): List<T> = items
    abstract fun aggregate(items: List<T>, sortOption: () -> SortOptions): List<R>

    fun allInOne(
            curPath: String,
            imageVideoGifFilter: ImageVideoGifFilter,
            filterBit: Long, sortOption: SortOptions,
            items: List<T>
    ): List<R> {
        return items.let {
            map(it, { curPath })
        }.let {
            filter(it, { medium -> imageVideoGifFilter.filterd(medium, filterBit) == NOT_FILTERED })
        }.let {
            sort(it, { sortOption })
        }.let {
            aggregate(it, { sortOption })
        }
    }

    class DirectoryMediumMapper : SubjectMapper<Medium, Medium>() {
        override fun map(items: List<Medium>, curPath: () -> String): List<Medium> {
            return items.filter { it.path.startsWith(curPath()) }
        }


        override fun filter(items: List<Medium>, filter: (Medium) -> Boolean): List<Medium> {
            return items.filter { filter(it) }
        }


        override fun sort(items: List<Medium>, sortOption: () -> SortOptions): List<Medium> {
            return items.sortedWith(SortComparatorFactory.createComparator(sortOption()))
        }


        override fun aggregate(items: List<Medium>, sortOption: () -> SortOptions): List<Medium> {
            return items
        }

    }

    class DirecotryRootMapper(private val dirTransformer: DirTransformer) : SubjectMapper<Medium, Directory>() {
        override fun aggregate(items: List<Medium>, sortOption: () -> SortOptions): List<Directory> =
                dirTransformer.transform(sortOption(), items)
    }

    class DateMediumMapper(private val dateTransformer: DateTransformer) : SubjectMapper<Medium, Basic>() {
        override fun aggregate(items: List<Medium>, sortOption: () -> SortOptions): List<Basic> =
                dateTransformer.transform(sortOption(), items)
    }
}

