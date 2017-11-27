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
class ReadOnlyApplicationConfig @Inject constructor(
        application: Application
) : ApplicationConfig(application) {

    override var filterdType: Long
        get() = super.filterdType
        set(value) {
            throwReadOnlyException()
        }
    override var sortOptionType: SortOptions
        get() = super.sortOptionType
        set(value) {
            throwReadOnlyException()
        }
    override var showHidden: Boolean
        get() = super.showHidden
        set(value) {
            throwReadOnlyException()
        }
    override var categoryMode: CategoryMode
        get() = super.categoryMode
        set(value) {
            throwReadOnlyException()
        }
    override var viewModeType: ViewModeType
        get() = super.viewModeType
        set(value) {
            throwReadOnlyException()
        }

    private fun throwReadOnlyException() {
        throw IllegalAccessException("read only")
    }
}
