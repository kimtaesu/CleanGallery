package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-13.
 */
class ImageVideoGifFilter @Inject constructor(private val config: ApplicationConfig) : MediaTypeFilter {
    override fun filterd(medium: Medium, noMedia: Set<String>): Boolean {
        val isImage = MediaTypeHelper.isImage(medium.name) && config.filterdType and IMAGES > 0
        if (isImage) return false
        val isVideo = MediaTypeHelper.isVideo(medium.name) && config.filterdType and VIDEOS > 0
        if (isVideo) return false
        val isGif = MediaTypeHelper.isGif(medium.name) && config.filterdType and GIFS > 0
        if (isGif) return false
        return true
    }
}