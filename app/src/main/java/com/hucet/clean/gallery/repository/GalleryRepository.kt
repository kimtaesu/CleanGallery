package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository(
        private val localDataSource: LocalDataSource,
        private val transformer: MediumTransformer
) {
    fun getGalleries(readOnlyConfigs: ReadOnlyConfigs, isRoot: Boolean, cacheInvalidate: Boolean): Flowable<List<Basic>> {
        return localDataSource.getGalleries(cacheInvalidate, readOnlyConfigs)
                .map {
                    transformer.transform(it, isRoot, readOnlyConfigs)
                }
    }
}
