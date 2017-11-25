package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.gallery.adapter.GalleryType

/**
 * Created by taesu on 2017-10-30.
 */

data class Directory(
        val count: Int,
        val medium: Medium,
        override val id: Long,
        override val name: String,
        override val path: String,
        override val viewType: GalleryType = GalleryType.DIRECTORY
) : Basic() {
}
