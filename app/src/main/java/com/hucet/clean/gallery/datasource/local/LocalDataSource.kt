package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.extension.getExternalStorageDirectory
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource constructor(
        private val mediaFetcher: MediaFetcher
) {
    private var flowableCache: Flowable<List<Medium>>? = null

    private fun fetch(): Flowable<List<Medium>> {
        return Flowable
                .defer {
                    Timber.d("GalleryPresenter getGalleries")
                    val cursor = mediaFetcher.query(getExternalStorageDirectory())
                    Flowable.just(mediaFetcher.parseCursor(cursor))
                }
                .cache()
    }

    fun getGalleries(cacheInvalidate: Boolean): Flowable<List<Medium>> {
        return if (cacheInvalidate) {
            flowableCache = fetch()
            flowableCache!!
        } else {
            if (flowableCache == null)
                flowableCache = fetch()
            flowableCache!!
        }
    }
}