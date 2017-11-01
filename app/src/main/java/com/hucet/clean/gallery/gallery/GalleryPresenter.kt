package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.repository.GalleryRepository

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryPresenter constructor(val view: Gallery.View,
                                   val adapter: GalleryAdapter,
                                   val repository: GalleryRepository
) : Gallery.Presenter {

    override fun fetchItems() {
        repository
                .getGalleries()
                .subscribe(
                        { next ->
                            adapter.updateData(next)
                        },
                        { error ->
                            view.showError()
                            error.printStackTrace()
                        },
                        {
                            view.hideProgress()
                        })
    }
}