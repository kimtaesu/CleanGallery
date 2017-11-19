package com.hucet.clean.gallery.config

import android.app.Application
import com.hucet.clean.gallery.activity.MainActivity
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

    private var sortOptionType: SortOptionType
        get() {
            when (categoryMode) {
                CategoryMode.DATE -> {
                    val bitSort = PreferenceHelper.defaultPrefs(application)[key_date_sorting, SORT_BY_DAILY or SORT_DESCENDING]
                    val sortType = SortOptionType.getFromSortOrderBit(bitSort)
                    sortType validate categoryMode
                    return sortType
                }
                CategoryMode.DIRECTORY -> {
                    val bitSort = PreferenceHelper.defaultPrefs(application)[key_dir_sorting, SORT_BY_DATE_MODIFIED or SORT_DESCENDING]
                    val sortType = SortOptionType.getFromSortOrderBit(bitSort)
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

    private var categoryMode: CategoryMode
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_category_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_category_type, CategoryMode.DIRECTORY.name]
            return CategoryMode.valueOf(name)
        }

    private var viewModeType: ViewModeType
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_layout_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_layout_type, ViewModeType.LINEAR.name]
            return ViewModeType.valueOf(name)
        }


    fun getReadOnlyConfigs(activity: MainActivity): ReadOnlyConfigs {
        validateSetGetter(activity)
        return ReadOnlyConfigs(
                this.categoryMode,
                this.viewModeType,
                this.sortOptionType
        )
    }

    fun setReadOnlyConfigs(activity: MainActivity, readOnlyConfigs: ReadOnlyConfigs): ReadOnlyConfigs {
        validateSetGetter(activity)
        this.sortOptionType = readOnlyConfigs.getSortOptionType()
        this.categoryMode = readOnlyConfigs.getCategoryMode()
        this.viewModeType = readOnlyConfigs.getViewModeType()
        return getReadOnlyConfigs(activity)
    }

    fun setReadOnlyConfigs(activity: MainActivity, viewMode: ViewModeType): ReadOnlyConfigs {
        validateSetGetter(activity)
        this.viewModeType = viewMode
        return getReadOnlyConfigs(activity)
    }

    fun setReadOnlyConfigs(activity: MainActivity, sortOptionType: SortOptionType): ReadOnlyConfigs {
        validateSetGetter(activity)
        this.sortOptionType = sortOptionType
        return getReadOnlyConfigs(activity)
    }


    fun setReadOnlyConfigs(activity: MainActivity, categoryMode: CategoryMode): ReadOnlyConfigs {
        validateSetGetter(activity)
        this.categoryMode = categoryMode
        return getReadOnlyConfigs(activity)
    }

    private fun validateSetGetter(mainActivity: MainActivity) {
        if (mainActivity !is MainActivity)
            throw IllegalArgumentException()
    }
}

