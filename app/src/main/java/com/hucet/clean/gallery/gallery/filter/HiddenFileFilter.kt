package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.model.Medium
import javax.inject.Inject

/**
 * Created by taesu on 2017-11-13.
 */
class HiddenFileFilter @Inject constructor(private val config: ApplicationConfig) : MediaTypeFilter {
    override fun filterd(medium: Medium, noMedia: Set<String>): Boolean {
        val isHiddenFile = isHidden(medium, noMedia)

        if (!isHiddenFile)
            return MediaTypeFilter.NOT_FILTERED

        if (config.showHidden && isHiddenFile)
            return MediaTypeFilter.NOT_FILTERED

        return MediaTypeFilter.FILTERED
    }

    private fun isHidden(medium: Medium, noMedia: Set<String>): Boolean {
        return matchNoMedia(medium, noMedia) || isHiddenFolder(medium)
    }

    private fun matchNoMedia(medium: Medium, noMedia: Set<String>): Boolean {
        return noMedia.any {
            medium.path.startsWith(it)
        }
    }

    private fun isHiddenFolder(medium: Medium): Boolean {
        return medium.path.contains("/.")
    }
}