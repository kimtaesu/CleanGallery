package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository(private val localDataSource: LocalDataSource) {
    fun getGalleries(): Flowable<List<Medium>> {
        return Flowable.defer({
            localDataSource.getGalleries()
        })
    }
}
