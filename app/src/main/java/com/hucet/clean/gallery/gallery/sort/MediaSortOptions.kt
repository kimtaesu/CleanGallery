package com.hucet.clean.gallery.gallery.sort

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.category.CategoryMode

/**
 * Created by taesu on 2017-11-12.
 */
class MediaSortOptions {
    companion object {
        fun getSortOptions(config: ApplicationConfig): String {
            if (config.categoryMode == CategoryMode.DATE)
                return SortOptions(SortOptionType.BY_MODIFIED, ByOrder.BY_DESC).getSortOptionString()
            return config.getDirSortType().getSortOptionString()
        }
    }
}