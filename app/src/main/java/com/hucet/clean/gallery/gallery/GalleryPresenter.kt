package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.repository.GalleryRepository
import com.hucet.clean.gallery.scheduler.DefaultSchedulerProvider
import com.hucet.clean.gallery.scheduler.SchedulerProvider
import timber.log.Timber

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryPresenter constructor(private val view: Gallery.View,
                                   private val adapter: GalleryAdapter,
                                   private val repository: GalleryRepository,
                                   private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : Gallery.Presenter {

    override fun fetchItems() {
        repository
                .getGalleries()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .doOnSubscribe {
                    Timber.d("doOnSubscribe")
                    view.showProgress()
                }
                .observeOn(schedulerProvider.main())
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