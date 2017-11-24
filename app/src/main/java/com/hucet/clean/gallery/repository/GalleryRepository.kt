package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.sort.SortComparatorFactory
import com.hucet.clean.gallery.model.Basic
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository(
        private val localDataSource: LocalDataSource,
        private val transformer: MediumTransformer
) {
    fun getGalleries(readOnlyConfigs: ReadOnlyConfigs, curPath: String, isRoot: Boolean, cacheInvalidate: Boolean): Flowable<List<Basic>> {
        return localDataSource.getGalleries(cacheInvalidate, readOnlyConfigs)
                .map {
                    if (!isRoot)
                        it.filter { it.path.startsWith(curPath) }
                    else
                        it
                }
                .map {
                    if (!isRoot)
                        it.sortedWith(SortComparatorFactory.createComparator(readOnlyConfigs.getSortOptionType()))
                    else
                        it
                }
                .map {
                    transformer.transform(it, isRoot, readOnlyConfigs)
                }
    }
}
