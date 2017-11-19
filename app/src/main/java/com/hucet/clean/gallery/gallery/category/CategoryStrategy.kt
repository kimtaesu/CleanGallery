package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-10.
 */
interface CategoryStrategy<R> {
    fun classify(sortOptionType: SortOptionType, items: List<Medium>): List<R>
}