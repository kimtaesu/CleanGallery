package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.gallery.adapter.GalleryType

/**
 * Created by taesu on 2017-11-09.
 */
abstract class Basic() {
    abstract val id: Long
    abstract val name: String
    abstract val path: String
    abstract val viewType: GalleryType
}
