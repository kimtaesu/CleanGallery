package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.datasource.local.LocalDataSource

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository(private val localDataSource: LocalDataSource) {
    fun getGalleries(cacheInvalidate : Boolean) = localDataSource.getGalleries(cacheInvalidate)
}
