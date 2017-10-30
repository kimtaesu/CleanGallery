package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.model.Gallery
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
interface GalleryRepository {
    fun getGalleries(): Flowable<List<Gallery>>
}