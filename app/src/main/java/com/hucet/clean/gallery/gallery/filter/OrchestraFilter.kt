package com.hucet.clean.gallery.gallery.filter

import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.FILTERED
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter.Companion.NOT_FILTERED
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-15.
 */
class OrchestraFilter constructor(
        filters: Set<@JvmSuppressWildcards MediaTypeFilter>,
        private val config: ApplicationConfig
) {
    private val FILTER_HIGH_PRIORITY = listOf(
            ImageVideoGifFilter::class
    )
    private val orderedFilter = LinkedHashSet<MediaTypeFilter>()

    init {
        orderedFilter.addAll(
                filters.sortedByDescending {
                    FILTER_HIGH_PRIORITY.indexOf(it::class)
                }
        )
    }

    operator fun iterator(): LinkedHashSet<MediaTypeFilter> {
        return orderedFilter
    }

    fun filterd(medium: Medium, noMedia: Set<String>): Boolean {
        val isFilter = orderedFilter.any {
            when (it::class) {
                ImageVideoGifFilter::class -> {
                    it as ImageVideoGifFilter
                    it.filterd(medium, IMAGES or VIDEOS or GIFS)
                }
                HiddenFileFilter::class -> {
                    it as HiddenFileFilter
                    it.filterd(medium, noMedia, config.showHidden)
                }
                else -> {
                    FILTERED
                }
            }
        }
        return if (isFilter) FILTERED
        else NOT_FILTERED
    }
}