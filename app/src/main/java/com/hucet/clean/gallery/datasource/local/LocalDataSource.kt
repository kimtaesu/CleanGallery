package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.datasource.GalleryDataSource
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource constructor(
        private val mediaFetcher: MediaFetcher,
        private val noMediaFolderProvider: NoMediaFolderProvider
) : GalleryDataSource {
    override fun getGalleries(curPath: String, readOnlyConfigs: ReadOnlyConfigs): Flowable<List<Medium>> {
        return Flowable
                .defer {
                    Timber.d("GalleryPresenter noMedia")
                    val cur = noMediaFolderProvider.query()
                    Flowable.just(noMediaFolderProvider.parseCursor(cur))
                }
                .map { noMediaFolder ->
                    Timber.d("GalleryPresenter getGalleries")
                    val cursor = mediaFetcher.query(curPath, readOnlyConfigs.getSortOptionType().media(readOnlyConfigs.getCategoryMode()))
                    mediaFetcher.parseCursor(cursor, noMediaFolder)
                }
    }
}