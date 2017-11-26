package com.hucet.clean.gallery.presenter

import android.arch.lifecycle.LifecycleObserver
import com.hucet.clean.gallery.gallery.directory.PathLocationContext

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
        fun fetchItems(pathLocationContext: PathLocationContext, cacheInvalidate: Boolean)
    }
}
