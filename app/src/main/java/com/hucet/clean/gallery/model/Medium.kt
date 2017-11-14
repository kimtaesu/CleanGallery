package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.config.SORT_BY_DATE_MODIFIED
import com.hucet.clean.gallery.config.SORT_BY_NAME
import com.hucet.clean.gallery.config.SORT_BY_SIZE
import com.hucet.clean.gallery.config.SORT_DESCENDING
import com.hucet.clean.gallery.extension.*
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import java.io.Serializable

/**
 * Created by taesu on 2017-10-30.
 */

data class Medium(
        override val id: Long,
        override val name: String,
        override val path: String,
        private val _modified: Long,
        val taken: Long,
        val size: Long,
        val isVideo: Boolean = false,
        override val viewType: GalleryType = GalleryType.MEDIUM
) : Basic(), Serializable {
    var modified: Long = _modified
        get() = _modified * 1000L
}
