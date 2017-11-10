package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.config.SORT_BY_DATE_MODIFIED
import com.hucet.clean.gallery.config.SORT_BY_NAME
import com.hucet.clean.gallery.config.SORT_BY_SIZE
import com.hucet.clean.gallery.config.SORT_DESCENDING
import com.hucet.clean.gallery.extension.*
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import java.io.Serializable

/**
 * Created by taesu on 2017-10-30.
 */

data class Medium(
        override val id: Long,
        override val name: String,
        override val path: String,
        val modified: Long,
        val taken: Long,
        val size: Long,
        override val viewType: GalleryAdapter.GalleryType = GalleryAdapter.GalleryType.MEDIUM
) : Basic(), Serializable
