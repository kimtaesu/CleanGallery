package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.config.SORT_BY_DATE_MODIFIED
import com.hucet.clean.gallery.config.SORT_BY_NAME
import com.hucet.clean.gallery.config.SORT_BY_SIZE
import com.hucet.clean.gallery.config.SORT_DESCENDING
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import java.io.Serializable

/**
 * Created by taesu on 2017-10-30.
 */

data class Directory(override val id: Long,
                     val dirName: String,
                     val absolutePath: String,
                     val firstMedium: Medium,
                     val count: Int,
                     override val viewType: GalleryAdapter.GalleryType = GalleryAdapter.GalleryType.DIRECTORY
) : Basic(), Serializable

