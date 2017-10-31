package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.repository.GalleryRepository

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryPresenter : Gallery.Presenter {
    private var repository = GalleryRepository()
    lateinit var view: Gallery.View
    lateinit var adapter: GalleryAdapter

    override fun fetchItems() {
        repository
                .getGalleries()
                .subscribe(
                        { next ->
                            adapter.updateData(next)
                        },
                        { error ->
                            view.showError()
                        },
                        {
                            view.hideProgress()
                        })
    }
}