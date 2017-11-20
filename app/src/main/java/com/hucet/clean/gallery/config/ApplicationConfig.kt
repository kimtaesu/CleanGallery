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
    private var filterdType: Int
        get() {
            return PreferenceHelper.defaultPrefs(application)[key_filter_media, VIDEOS or IMAGES or GIFS]
        }
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_filter_media] = value
            value
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

    var showHidden: Boolean
        get() {
            return PreferenceHelper.defaultPrefs(application)[key_show_hidden, false]
        }
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_show_hidden] = value
            value
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

    fun ReadOnlyConfigBuild(init: ReadOnlyConfigBuilder.() -> Unit): ReadOnlyConfigs {
        return ReadOnlyConfigBuilder(init).build()
    }

    inner class ReadOnlyConfigBuilder() {
        constructor(init: ReadOnlyConfigBuilder.() -> Unit) : this() {
            init()
        }

        fun filterType(filterBit: Int) {
            this@ApplicationConfig.filterdType = filterBit
        }

        fun categoryMode(categoryMode: CategoryMode) {
            this@ApplicationConfig.categoryMode = categoryMode
//            TODO Test Code
            if (categoryMode == CategoryMode.DATE)
                viewMode(ViewModeType.GRID)
        }

        fun sortType(sortOptionType: SortOptionType) {
            this@ApplicationConfig.sortOptionType = sortOptionType
        }

        fun viewMode(viewMode: ViewModeType) {
            this@ApplicationConfig.viewModeType = viewMode
        }

        fun showHidden(isShow: Boolean) {
            this@ApplicationConfig.showHidden = isShow
        }

        fun build(): ReadOnlyConfigs {
            return ReadOnlyConfigs(
                    c = this@ApplicationConfig.categoryMode,
                    s = this@ApplicationConfig.sortOptionType,
                    v = this@ApplicationConfig.viewModeType,
                    f = this@ApplicationConfig.filterdType,
                    varShowHidden = this@ApplicationConfig.showHidden
            )
        }
    }

}
