package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-13.
 */
class HiddenFileFilter(private val config: ApplicationConfig) : MediaTypeFilter {
    override fun filterd(medium: Medium): Boolean {
        return true
    }
}