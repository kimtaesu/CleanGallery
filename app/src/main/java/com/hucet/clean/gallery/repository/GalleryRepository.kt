package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.directory.DirectoryContext
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.model.Basic
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository(
        private val localDataSource: LocalDataSource,
        private val transformer: MediumTransformer,
        private val imageVideoGifFilter: ImageVideoGifFilter
) {
    fun getGalleries(directoryContext: DirectoryContext, readOnlyConfigs: ReadOnlyConfigs, cacheInvalidate: Boolean): Flowable<List<Basic>> {
        return localDataSource.getGalleries(cacheInvalidate, readOnlyConfigs)
                .switchMap {
                    directoryContext.map(it)
                }
//
//                .map { it ->
//                    if (!directoryContext)
//                        it.filter { it.path.startsWith(curPath) }
//                    else
//                        it
//                }
//                .map {
//                    if (!isRoot) {
//                        it.filter {
//                            imageVideoGifFilter.filterd(it, readOnlyConfigs.getFilterBit()) == NOT_FILTERED
//                        }
//                    } else
//                        it
//                }
//                .map {
//                    if (!isRoot)
//                        it.sortedWith(SortComparatorFactory.createComparator(readOnlyConfigs.getSortOptionType()))
//                    else
//                        it
//                }
//                .map {
//                    transformer.transform(it, isRoot, readOnlyConfigs)
//                }
    }
}
