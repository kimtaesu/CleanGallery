package com.hucet.clean.gallery.config

import android.app.Application
import com.hucet.clean.gallery.gallery.category.CategoryType
import com.hucet.clean.gallery.gallery.category.DateClassifier
import com.hucet.clean.gallery.gallery.fragment.LayoutType
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


    fun getDateSortType(path: String): DateClassifier.DATE_SORT_TYPE {
        val sort = PreferenceHelper.defaultPrefs(application)[key_date_sorting + path.toLowerCase(), SORT_BY_DAILY]
        return mapDateSortType(sort)
    }

    private fun mapDateSortType(sort: Int): DateClassifier.DATE_SORT_TYPE {
        return when {
            sort and SORT_BY_DAILY > 0 -> {
                DateClassifier.DATE_SORT_TYPE.DAILY
            }
            sort and SORT_BY_MONTHLY > 0 -> {
                DateClassifier.DATE_SORT_TYPE.MONTHLY
            }
            sort and SORT_BY_YEARLY > 0 -> {
                DateClassifier.DATE_SORT_TYPE.YEARLY
            }
            else -> throw IllegalArgumentException()
        }
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
