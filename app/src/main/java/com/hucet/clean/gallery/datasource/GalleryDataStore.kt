package com.hucet.clean.gallery.datasource

import com.hucet.clean.gallery.datasource.local.LocalMapper
import com.hucet.clean.gallery.model.Basic
import com.hucet.clean.gallery.model.Directory
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
interface GalleryDataStore {
    fun getGalleries(curPath: String): Flowable<Map<String, List<Basic>>>
}
