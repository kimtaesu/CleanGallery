package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS

/**
 * Created by taesu on 2017-11-13.
 */
class MediaTypeFilter(private val config: ApplicationConfig) {
    fun filterd(fileName: String): Boolean {
        val isImage = MediaTypeHelper.isImage(fileName) && config.filterdType and IMAGES > 0
        if (isImage) return false
        val isVideo = MediaTypeHelper.isVideo(fileName) && config.filterdType and VIDEOS > 0
        if (isVideo) return false
        val isGif = MediaTypeHelper.isGif(fileName) && config.filterdType and GIFS > 0
        if (isGif) return false
        return true
    }

    private object MediaTypeHelper {
        private val photoExtensions: Array<String> = arrayOf(".jpg", ".png", ".jpeg", ".bmp", ".webp")
        private val videoExtensions: Array<String> = arrayOf(".mp4", ".mkv", ".webm", ".avi", ".3gp", ".mov", ".m4v", ".3gpp")
        // fast extension checks, not guaranteed to be accurate

        fun isVideo(fileName: String) = videoExtensions.any { fileName.endsWith(it, true) }
        fun isImage(fileName: String) = photoExtensions.any { fileName.endsWith(it, true) }
        fun isGif(fileName: String) = fileName.endsWith(".gif", true)
    }
}