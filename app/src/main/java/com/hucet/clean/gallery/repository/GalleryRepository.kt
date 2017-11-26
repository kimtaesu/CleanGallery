package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.gallery.directory.PathLocationContext
import com.hucet.clean.gallery.model.Basic
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository(
        private val localDataSource: LocalDataSource
) {
    fun getGalleries(pathLocationContext: PathLocationContext, cacheInvalidate: Boolean): Flowable<List<Basic>> {
        return localDataSource.getGalleries(cacheInvalidate)
                .switchMap {
                    Flowable.just(pathLocationContext.switchMap(it))
                }
    }
}
