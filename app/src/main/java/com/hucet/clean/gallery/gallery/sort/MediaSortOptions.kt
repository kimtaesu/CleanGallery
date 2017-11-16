package com.hucet.clean.gallery.gallery.sort

import android.provider.MediaStore
import com.hucet.clean.gallery.config.*
import com.hucet.clean.gallery.gallery.category.CategoryMode

/**
 * Created by taesu on 2017-11-12.
 */
class MediaSortOptions {
    companion object {
        fun getSortOptions(curPath: String, config: ApplicationConfig): String {
            if (config.categoryMode == CategoryMode.DATE)
                return "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

            val sort = config.getDirSortType(curPath)
            val options = when {
                sort and SORT_BY_NAME > 0 -> MediaStore.Images.Media.DISPLAY_NAME
                sort and SORT_BY_DATE_TAKEN > 0 -> MediaStore.Images.Media.DATE_TAKEN
                sort and SORT_BY_PATH > 0 -> MediaStore.Images.Media.DATA
                sort and SORT_BY_SIZE > 0 -> MediaStore.Images.Media.SIZE
                sort and SORT_BY_DATE_MODIFIED > 0 -> MediaStore.Images.Media.DATE_MODIFIED
                else -> MediaStore.Images.Media.DATE_MODIFIED
            }

            val order = if (sort and SORT_DESCENDING > 0) "DESC"
            else "ASC"

            return "${options} ${order}"
        }
    }
}