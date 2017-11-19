package com.hucet.clean.gallery.datasource

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.model.Medium
import io.reactivex.Flowable

/**
 * Created by taesu on 2017-10-30.
 */
interface GalleryDataSource {
    fun getGalleries(curPath: String, sortOptionType: ReadOnlyConfigs): Flowable<List<Medium>>
}
