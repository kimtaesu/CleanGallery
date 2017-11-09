package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter

/**
 * Created by taesu on 2017-11-09.
 */
abstract class Basic() {
    abstract val id: Long
    abstract val viewType: GalleryAdapter.GalleryType
}
