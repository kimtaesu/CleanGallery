package com.hucet.clean.gallery.config

import android.app.Application
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.gallery.sort.SortOptionType
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

    var sortOptionType: SortOptionType
        get() {
            when (categoryMode) {
                CategoryMode.DATE -> {
                    val bitSort = PreferenceHelper.defaultPrefs(application)[key_date_sorting, SORT_BY_DAILY]
                    val sortType = SortOptionType.get(bitSort)
                    sortType validate categoryMode
                    return sortType
                }
                CategoryMode.DIRECTORY -> {
                    val bitSort = PreferenceHelper.defaultPrefs(application)[key_dir_sorting, SORT_BY_DAILY]
                    val sortType = SortOptionType.get(bitSort)
                    sortType validate categoryMode
                    return sortType
                }
            }
        }
        set(value) {
            when (categoryMode) {
                CategoryMode.DATE -> {
                    PreferenceHelper.defaultPrefs(application)[key_date_sorting] = value.bitWithOrder()
                }
                CategoryMode.DIRECTORY -> {
                    PreferenceHelper.defaultPrefs(application)[key_dir_sorting] = value.bitWithOrder()
                }
            }
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
