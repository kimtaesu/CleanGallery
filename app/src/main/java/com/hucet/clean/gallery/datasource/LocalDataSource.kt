package com.hucet.clean.gallery.datasource

import com.hucet.clean.gallery.model.Gallery
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */

class LocalDataSource : GalleryDataStore {
    override fun getGalleries(): Flowable<List<Gallery>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}