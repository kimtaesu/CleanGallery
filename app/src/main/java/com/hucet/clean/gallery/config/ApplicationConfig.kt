package com.hucet.clean.gallery.config

import android.app.Application
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.gallery.sort.SortOptionType
import com.hucet.clean.gallery.gallery.sort.SortOptions
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-30.
 */
class ApplicationConfig @Inject constructor(
        val application: Application
) {
    var filterdType: Int = PreferenceHelper.defaultPrefs(application)[key_filter_media, VIDEOS or IMAGES or GIFS]
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_filter_media] = value
        }

    fun getDirSortType(): SortOptions {
        //         TODO each sort
//        return PreferenceHelper.defaultPrefs(application)[key_dir_sorting + path.toLowerCase(), SORT_BY_DATE_MODIFIED or SORT_DESCENDING]
        val sort = PreferenceHelper.defaultPrefs(application)[key_dir_sorting, SORT_BY_DATE_MODIFIED or SORT_DESCENDING]
        return wrapSortOptionType(sort)
    }


    fun getDateSortType(): SortOptions {
//         TODO each sort
//        val sort = PreferenceHelper.defaultPrefs(application)[key_date_sorting + path.toLowerCase(), SORT_BY_DAILY]
        val sort = PreferenceHelper.defaultPrefs(application)[key_date_sorting, SORT_BY_DAILY]
        return wrapSortOptionType(sort)
    }

    private fun wrapSortOptionType(bitSort: Int): SortOptions {
        val sortOptionType = SortOptionType.values().first {
            bitSort and it.bitAttr > 0
        }
        sortOptionType validate categoryMode
        val byOrder = sortOptionType byOrder bitSort
        return SortOptions(sortOptionType, byOrder)
    }

    val showHidden: Boolean
        get() {
            return PreferenceHelper.defaultPrefs(application)[key_show_hidden, false]
        }

    var categoryMode: CategoryMode
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_category_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_category_type, CategoryMode.DIRECTORY.name]
            return CategoryMode.valueOf(name)
        }

    var viewModeType: ViewModeType
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_layout_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_layout_type, ViewModeType.LINEAR.name]
            return ViewModeType.valueOf(name)
        }
}
