package com.hucet.clean.gallery.datasource.local

import com.hucet.clean.gallery.datasource.GalleryDataSource
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import io.reactivex.Flowable
import timber.log.Timber
import java.io.File

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource constructor(private val madiaFetcher: MediaFetcher) : GalleryDataSource {
    override fun getGalleries(curPath: String): Flowable<Map<String, List<Basic>>> {
        return Flowable.defer {
            Timber.d("GalleryPresenter getGalleries")
            val cursor = madiaFetcher.queryImage(curPath)
            val result = madiaFetcher.category(MediaFetcher.CategoryType.DIR, madiaFetcher.parseCursor(cursor))
            if (curPath.isExternalStorageDir()) {
                val map = result.map {
                    val first = it.value.first()
                    it.key to listOf(Directory(first.id, File(it.key).name, it.key, first, it.value.size) as Basic)
                }.toMap()
                Flowable.just(map)
            } else {
                Flowable.just(result)
            }
        }
    }

}
