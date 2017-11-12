package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository(private val localDataSource: LocalDataSource) {
    fun getGalleries(curPath: String, isDirType: Boolean) = localDataSource.getGalleries(curPath, isDirType)
}
