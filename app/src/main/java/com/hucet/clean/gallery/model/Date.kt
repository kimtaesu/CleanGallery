package com.hucet.clean.gallery.model

import android.support.annotation.VisibleForTesting
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType

/**
 * Created by taesu on 2017-10-30.
 */

data class Date(
        val date: String,
        override val viewType: GalleryType = GalleryType.DATE,
        override val id: Long = 0,
        override val name: String = "",
        override val path: String = ""
) : Basic() {
}