package com.hucet.clean.gallery.datasource

import com.hucet.clean.gallery.model.Gallery
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
interface GalleryDataStore {
    fun getGalleries(): Flowable<List<Gallery>>
}