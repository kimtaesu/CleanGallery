package com.hucet.clean.gallery.datasource.local

import android.os.Environment
import com.hucet.clean.gallery.gallery.sort.ByOrder
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource constructor(
        private val mediaFetcher: MediaFetcher,
        private val noMediaFolderProvider: NoMediaFolderProvider
) {
    private var flowableCache: Flowable<List<Medium>>? = null

    private fun fetch(): Flowable<List<Medium>> {
        return Flowable
                .defer {
                    Timber.d("GalleryPresenter noMedia")
                    val cur = noMediaFolderProvider.query()
                    Flowable.just(noMediaFolderProvider.parseCursor(cur))
                }
                .map { noMediaFolder ->
                    Timber.d("GalleryPresenter getGalleries")
                    val cursor = mediaFetcher.query(Environment.getExternalStorageDirectory().path, "${SortOptionType.BY_MODIFIED.option} ${ByOrder.BY_DESC.option}")
                    mediaFetcher.parseCursor(cursor, noMediaFolder)
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