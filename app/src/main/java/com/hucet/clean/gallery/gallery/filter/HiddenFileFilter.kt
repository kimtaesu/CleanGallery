package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-13.
 */
class HiddenFileFilter @Inject constructor(private val config: ApplicationConfig) : MediaTypeFilter {
    override fun filterd(medium: Medium, noMedia: Set<String>): Boolean {
        var isFilter = true
        if (isHidden(medium.path, noMedia))
            isFilter = config.showHidden
        return isFilter
    }

    private fun isHidden(path: String, noMedia: Set<String>): Boolean {
        return noMedia.any {
            path.startsWith(it)
        }
    }
}