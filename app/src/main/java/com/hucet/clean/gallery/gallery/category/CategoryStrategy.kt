package com.hucet.clean.gallery.gallery.category

import com.hucet.clean.gallery.model.Medium

/**
 * Created by taesu on 2017-11-10.
 */
interface CategoryStrategy {
    fun <T : Medium> category(items: List<Medium>): Map<String, List<T>>
}