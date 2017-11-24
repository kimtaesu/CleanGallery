package com.hucet.clean.gallery.presenter

import android.arch.lifecycle.LifecycleObserver
import com.hucet.clean.gallery.config.ReadOnlyConfigs

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
        fun fetchItems(curPath: String, isRoot : Boolean, readOnlyConfigs: ReadOnlyConfigs, cacheInvalidate: Boolean)
    }
}
