package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.datasource.GalleryDataSource
import com.hucet.clean.gallery.gallery.category.DirClassifier
import com.hucet.clean.gallery.gallery.sort.MediaSortOptions
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource constructor(
        private val mediaFetcher: MediaFetcher,
        private val config: ApplicationConfig
) : GalleryDataSource {
    override fun getGalleries(curPath: String, isDirType: Boolean): Flowable<List<Medium>> {
        return Flowable.defer {
            Timber.d("GalleryPresenter getGalleries")
            val cursor = mediaFetcher.query(curPath, MediaSortOptions.getSortOptions(curPath, config, isDirType))
            Flowable.just(mediaFetcher.parseCursor(cursor))
        }
    }
}
