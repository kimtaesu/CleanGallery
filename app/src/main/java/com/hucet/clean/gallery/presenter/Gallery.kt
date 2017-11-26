package com.hucet.clean.gallery.presenter

import android.arch.lifecycle.LifecycleObserver
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.gallery.directory.DirectoryContext

/**
 * Created by taesu on 2017-10-31.
 */
interface Gallery {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun showError()

    }

    interface Presenter : LifecycleObserver {
        fun fetchItems(directoryContext: DirectoryContext, readOnlyConfigs: ReadOnlyConfigs, cacheInvalidate: Boolean)
    }
}
