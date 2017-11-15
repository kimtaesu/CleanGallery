package com.hucet.clean.gallery.config

import android.app.Application
import com.hucet.clean.gallery.gallery.category.CategoryType
import com.hucet.clean.gallery.gallery.list.LayoutType
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-30.
 */
class ApplicationConfig @Inject constructor(
        val application: Application
) {
    val filterdType: Int = PreferenceHelper.defaultPrefs(application)[key_filter_media, VIDEOS or IMAGES or GIFS]

    fun getDirSortType(path: String): Int {
        return PreferenceHelper.defaultPrefs(application)[key_dir_sorting + path.toLowerCase(), SORT_BY_DATE_MODIFIED or SORT_DESCENDING]
    }

    fun getDateSortType(path: String): Int {
        return PreferenceHelper.defaultPrefs(application)[key_date_sorting + path.toLowerCase(), SORT_BY_DAILY or SORT_DESCENDING]
    }

    val showHidden: Boolean
        get() {
            return PreferenceHelper.defaultPrefs(application)[key_show_hidden, false]
        }

    var categoryType: CategoryType
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_category_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_category_type, CategoryType.DIRECTORY.name]
            return CategoryType.valueOf(name)
        }

    var layoutType: LayoutType
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_layout_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_layout_type, LayoutType.LINEAR.name]
            return LayoutType.valueOf(name)
        }
}
