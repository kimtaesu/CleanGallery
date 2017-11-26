package com.hucet.clean.gallery.config

import android.app.Application
import android.os.Environment
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.gallery.sort.SortOptions
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-30.
 */
class ApplicationConfig @Inject constructor(
        val application: Application
) {
//    private var curPath = Environment.getExternalStorageDirectory().absolutePath

    fun isRoot() = curPath.isExternalStorageDir()

    private var filterdType: Long
        get() {
            return PreferenceHelper.defaultPrefs(application)[key_filter_media, VIDEOS or IMAGES or GIFS]
        }
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_filter_media] = value
            value
        }

    private var sortOptionType: SortOptions
        get() {
            when (categoryMode) {
                CategoryMode.DATE -> {
                    val bitSort = PreferenceHelper.defaultPrefs(application)[key_date_sorting, SORT_BY_DAILY or SORT_DESCENDING]
                    val sortType = SortOptions.getFromSortBit(bitSort)
                    sortType.validate(categoryMode, isRoot())
                    return sortType
                }
                CategoryMode.DIRECTORY -> {
                    val bitSort = if (curPath.isExternalStorageDir())
                        PreferenceHelper.defaultPrefs(application)[key_dir_root_sorting, SORT_BY_DIR_PATH or SORT_DESCENDING]
                    else
                        PreferenceHelper.defaultPrefs(application)[key_dir_sorting, SORT_BY_DATE_MODIFIED or SORT_DESCENDING]
                    val sortType = SortOptions.getFromSortBit(bitSort)
                    sortType.validate(categoryMode, isRoot())
                    return sortType
                }
            }
        }
        set(value) {
            when (categoryMode) {
                CategoryMode.DATE -> {
                    PreferenceHelper.defaultPrefs(application)[key_date_sorting] = value.bit()
                }
                CategoryMode.DIRECTORY -> {
                    if (curPath.isExternalStorageDir())
                        PreferenceHelper.defaultPrefs(application)[key_dir_root_sorting] = value.bit()
                    else PreferenceHelper.defaultPrefs(application)[key_dir_sorting] = value.bit()
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

        fun filterType(filterBit: Long) {
            this@ApplicationConfig.filterdType = filterBit
        }

        fun categoryMode(categoryMode: CategoryMode) {
            this@ApplicationConfig.categoryMode = categoryMode
        }

        fun sortType(sortOptionType: SortOptions) {
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
