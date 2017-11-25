package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.config.VIDEOS
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-13.
 */
class ImageVideoGifFilter @Inject constructor() : MediaTypeFilter {

    fun filterd(medium: Medium, filterBit: Long): Boolean {
        val isImage = MediaTypeHelper.isImage(medium.name) && filterBit and IMAGES > 0
        if (isImage) return MediaTypeFilter.NOT_FILTERED
        val isVideo = MediaTypeHelper.isVideo(medium.name) && filterBit and VIDEOS > 0
        if (isVideo) return MediaTypeFilter.NOT_FILTERED
        val isGif = MediaTypeHelper.isGif(medium.name) && filterBit and GIFS > 0
        if (isGif) return MediaTypeFilter.NOT_FILTERED
        return MediaTypeFilter.FILTERED
    }

}