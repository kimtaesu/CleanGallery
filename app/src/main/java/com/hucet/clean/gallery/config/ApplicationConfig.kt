package com.hucet.clean.gallery.config

import android.app.Application
import com.hucet.clean.gallery.gallery.category.CategoryMode
import com.hucet.clean.gallery.gallery.directory.DirectoryRootChecker
import com.hucet.clean.gallery.gallery.view_mode.ViewModeType
import com.hucet.clean.gallery.gallery.sort.SortOptions
import javax.inject.Inject

/**
 * Created by taesu on 2017-10-30.
 */
open class ApplicationConfig @Inject constructor(
        private val application: Application
) {

    private lateinit var checker: DirectoryRootChecker

    fun setDirectoryRootChecker(rootChecker: DirectoryRootChecker) {
        this.checker = rootChecker
    }

    open var filterdType: Long
        get() {
            return PreferenceHelper.defaultPrefs(application)[key_filter_media, VIDEOS or IMAGES or GIFS]
        }
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_filter_media] = value
            value
        }

    open var sortOptionType: SortOptions
        get() {
            when (categoryMode) {
                CategoryMode.DATE -> {
                    val bitSort = PreferenceHelper.defaultPrefs(application)[key_date_sorting, SORT_BY_DAILY or SORT_DESCENDING]
                    val sortType = SortOptions.getFromSortBit(bitSort)
                    sortType.validate(categoryMode, checker.isRoot())
                    return sortType
                }
                CategoryMode.DIRECTORY -> {
                    val bitSort = if (checker.isRoot())
                        PreferenceHelper.defaultPrefs(application)[key_dir_root_sorting, SORT_BY_DIR_PATH or SORT_DESCENDING]
                    else
                        PreferenceHelper.defaultPrefs(application)[key_dir_sorting, SORT_BY_DATE_MODIFIED or SORT_DESCENDING]
                    val sortType = SortOptions.getFromSortBit(bitSort)
                    sortType.validate(categoryMode, checker.isRoot())
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
                    if (checker.isRoot()) PreferenceHelper.defaultPrefs(application)[key_dir_root_sorting] = value.bit()
                    else PreferenceHelper.defaultPrefs(application)[key_dir_sorting] = value.bit()
                }
            }
        }

    open var showHidden: Boolean
        get() {
            return PreferenceHelper.defaultPrefs(application)[key_show_hidden, false]
        }
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_show_hidden] = value
            value
        }

    open var categoryMode: CategoryMode
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_category_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_category_type, CategoryMode.DIRECTORY.name]
            return CategoryMode.valueOf(name)
        }

    fun isCategoryDate(): Boolean = categoryMode == CategoryMode.DATE

    open var viewModeType: ViewModeType
        set(value) {
            PreferenceHelper.defaultPrefs(application)[key_layout_type] = value.name
        }
        get() {
            val name = PreferenceHelper.defaultPrefs(application)[key_layout_type, ViewModeType.LINEAR.name]
            return ViewModeType.valueOf(name)
        }


}
