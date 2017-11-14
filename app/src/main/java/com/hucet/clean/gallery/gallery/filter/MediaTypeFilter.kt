package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-13.
 */
interface MediaTypeFilter {
    companion object {
        val FILTERED = true
        val NOT_FILTERED = false
    }

    fun filterd(medium: Medium, noMedia: Set<String>): Boolean
}