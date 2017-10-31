package com.hucet.clean.gallery.repository

import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
class GalleryRepository {
    fun getGalleries(): Flowable<List<Medium>> {
//        TODO Temp Code
        val dummy = arrayListOf(Medium("tyler", "/tyler", false, System.currentTimeMillis(), System.currentTimeMillis(), 10))
        return Flowable.just(dummy)
    }
}
