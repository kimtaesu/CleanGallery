package com.hucet.clean.gallery.model

import com.hucet.clean.gallery.gallery.adapter.GalleryType
import java.io.Serializable

/**
 * Created by taesu on 2017-10-30.
 */
enum class MediaType {
    VIDEO, IMAGE, GIF
}

data class Medium(
        override val id: Long,
        override val name: String,
        override val path: String,
        private val _modified: Long,
        val taken: Long,
        val size: Long,
        val orientation: Int,
        val mimeType: String,
        val mediaType: MediaType = MediaType.IMAGE,
        override val viewType: GalleryType = GalleryType.MEDIUM
) : Basic(), Serializable {
    var modified: Long = _modified
        get() = _modified * 1000L
}
