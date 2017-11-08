package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.datasource.GalleryDataStore
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

class LocalDataSource constructor(private val madiaFetcher: MediaFetcher) : GalleryDataStore {

    override fun getGalleries(curPath: String): Flowable<Map<String, List<Basic>>> {
        return Flowable.defer {
            Timber.d("GalleryPresenter getGalleries")
            val cursor = madiaFetcher.queryImage(curPath)
            val result = madiaFetcher.category(MediaFetcher.CategoryType.DIR, madiaFetcher.parseCursor(cursor))
            if (curPath.isExternalStorageDir()) {
                Flowable.just(result.map {
                    it.key to listOf(Directory(File(it.key).name, it.key, it.value.first(), it.value.size))
                }.toMap())
            } else {
                Flowable.just(result)
            }
        }
    }

}
