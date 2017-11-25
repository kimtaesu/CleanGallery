package com.hucet.clean.gallery.gallery.filter

import android.media.Image
import com.hucet.clean.gallery.config.GIFS
import com.hucet.clean.gallery.config.IMAGES
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.config.VIDEOS
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-15.
 */
class OrchestraFilter constructor(filters: Set<@JvmSuppressWildcards MediaTypeFilter>) {
    private val FILTER_HIGH_PRIORITY = listOf(
            ImageVideoGifFilter::javaClass.name
    )
    private val orderedFilter = LinkedHashSet<MediaTypeFilter>()

    init {
        orderedFilter.addAll(
                filters.sortedByDescending {
                    FILTER_HIGH_PRIORITY.indexOf(it::javaClass.name)
                }
        )
    }

    fun filterd(medium: Medium, noMedia: Set<String>, readOnlyConfigs: ReadOnlyConfigs): Boolean {
        val isFilter = orderedFilter.any {
            when (it::class) {
                ImageVideoGifFilter::class -> {
                    it as ImageVideoGifFilter
                    it.filterd(medium, IMAGES or VIDEOS or GIFS)
                }
                HiddenFileFilter::class -> {
                    it as HiddenFileFilter
                    it.filterd(medium, noMedia, readOnlyConfigs.showHidden)
                }
                else -> {
                    FILTERED
                }
            }
        }
        if (isFilter)
            return FILTERED
        return NOT_FILTERED
    }
}