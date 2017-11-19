package com.hucet.clean.gallery.presenter

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

    interface Presenter {
        fun fetchItems(curPath: String, readOnlyConfigs: ReadOnlyConfigs)
    }
}
