package com.hucet.clean.gallery.gallery.filter

/**
 * Created by taesu on 2017-11-15.
 */
class OrderedFilterContext constructor(filters: Set<@JvmSuppressWildcards MediaTypeFilter>) {
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

    operator fun iterator(): Set<MediaTypeFilter> {
        return orderedFilter
    }
}