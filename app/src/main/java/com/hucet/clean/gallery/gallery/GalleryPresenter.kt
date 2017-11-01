package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.extension.main
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.repository.GalleryRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

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
                .subscribeOn(Schedulers.io())
                .main()
                .doOnSubscribe {
                    Timber.d("doOnSubscribe")
                    view.showProgress()
                }
                .main()
                .doOnTerminate {
                    Timber.d("doOnTerminate")
                    view.hideProgress()
                }
                .subscribe(
                        { next ->
                            Timber.d("Next[${next}]")
                            adapter.updateData(next)
                        },
                        { error ->
                            Timber.d("Error[${error}]")
                            view.showError()
                        },
                        {
                            Timber.d("Completed")
                        })
    }

}