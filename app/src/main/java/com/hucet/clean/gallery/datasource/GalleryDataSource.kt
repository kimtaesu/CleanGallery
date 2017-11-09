package com.hucet.clean.gallery.datasource

import com.hucet.clean.gallery.model.Basic
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
interface GalleryDataSource {
    fun getGalleries(curPath: String): Flowable<Map<String, List<Basic>>>
}
