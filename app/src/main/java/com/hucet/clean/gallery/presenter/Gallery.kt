package com.hucet.clean.gallery.presenter

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
        fun fetchItems(curPath: String, isDirType: Boolean)
    }
}
