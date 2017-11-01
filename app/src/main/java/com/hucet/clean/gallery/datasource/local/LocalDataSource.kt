package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.datasource.GalleryDataStore
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource : GalleryDataStore {
    override fun getGalleries(): Flowable<List<Medium>> {
        Timber.d("GalleryPresenter getGalleries")
        val dummy = arrayListOf(Medium("tyler", "/tyler", false, System.currentTimeMillis(), System.currentTimeMillis(), 10))
        return Flowable.just(dummy)
    }
}