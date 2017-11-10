package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.datasource.GalleryDataSource
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable
import timber.log.Timber
import java.io.File

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource constructor(private val madiaFetcher: MediaFetcher) : GalleryDataSource {
    override fun getGalleries(curPath: String): Flowable<List<Medium>> {
        return Flowable.defer {
            Timber.d("GalleryPresenter getGalleries")
            val cursor = madiaFetcher.queryImage(curPath)
            Flowable.just(madiaFetcher.parseCursor(cursor))
        }
    }
}
