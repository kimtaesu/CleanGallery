package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-13.
 */
class ImageVideoGifFilter @Inject constructor() : MediaTypeFilter {
    override fun filterd(medium: Medium, noMedia: Set<String>, readOnlyConfigs: ReadOnlyConfigs): Boolean {
        val isImage = MediaTypeHelper.isImage(medium.name) && readOnlyConfigs.getFilterBit() and IMAGES > 0
        if (isImage) return MediaTypeFilter.NOT_FILTERED
        val isVideo = MediaTypeHelper.isVideo(medium.name) && readOnlyConfigs.getFilterBit() and VIDEOS > 0
        if (isVideo) return MediaTypeFilter.NOT_FILTERED
        val isGif = MediaTypeHelper.isGif(medium.name) && readOnlyConfigs.getFilterBit() and GIFS > 0
        if (isGif) return MediaTypeFilter.NOT_FILTERED
        return MediaTypeFilter.FILTERED
    }
}