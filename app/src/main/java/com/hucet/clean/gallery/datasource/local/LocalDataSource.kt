package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.datasource.GalleryDataStore
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource constructor(private val madiaFetcher: MediaFetcher) : GalleryDataStore {
    override fun getGalleries(curPath: String): Flowable<Map<String, List<Medium>>> {
        return Flowable.defer {
            Timber.d("GalleryPresenter getGalleries")
            val cursor = madiaFetcher.queryImage(curPath)
            val result = madiaFetcher.category(MediaFetcher.CategoryType.DIR, madiaFetcher.parseCursor(cursor))
            Flowable.just(result)
        }
    }
}