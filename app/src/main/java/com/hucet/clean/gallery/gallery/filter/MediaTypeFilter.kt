package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-13.
 */
interface MediaTypeFilter {
    fun filterd(medium: Medium): Boolean
}