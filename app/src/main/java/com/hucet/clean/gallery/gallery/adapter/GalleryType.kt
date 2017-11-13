package com.hucet.clean.gallery.gallery.adapter

/**
 * Created by taesu on 2017-11-13.
 */

enum class GalleryType(val value: Int) {
    DIRECTORY(0), MEDIUM(1), DATE(2);

    companion object {
        fun galleryType(value: Int): GalleryType {
            return when (value) {
                GalleryType.DIRECTORY.value -> GalleryType.DIRECTORY
                GalleryType.MEDIUM.value -> GalleryType.MEDIUM
                GalleryType.DATE.value -> GalleryType.DATE
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }

    }
}