package com.hucet.clean.gallery.model

import android.support.annotation.VisibleForTesting
import com.hucet.clean.gallery.config.SORT_BY_DATE_MODIFIED
import com.hucet.clean.gallery.config.SORT_BY_NAME
import com.hucet.clean.gallery.config.SORT_BY_SIZE
import com.hucet.clean.gallery.config.SORT_DESCENDING
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import java.io.Serializable

/**
 * Created by taesu on 2017-10-30.
 */

data class Directory(
        val count: Int,
        val thumbnail: String,
        override val id: Long,
        override val name: String,
        override val path: String,
        override val viewType: GalleryType = GalleryType.DIRECTORY
) : Basic() {
}
