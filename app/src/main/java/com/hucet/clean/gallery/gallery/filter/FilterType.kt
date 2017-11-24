package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.R
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.VIDEOS

/**
 * Created by taesu on 2017-11-20.
 */
enum class FilterType(val stringId: Int, val bitAtt: Long) {
    IMAGE(R.string.filter_images, IMAGES), VIDEO(R.string.filter_videos, VIDEOS), GIF(R.string.filter_gifs, GIFS);

    companion object {
        val KEY = FilterType::class.java.name
    }

    fun isChecked(filterBit: Long) = filterBit and bitAtt > 0
}