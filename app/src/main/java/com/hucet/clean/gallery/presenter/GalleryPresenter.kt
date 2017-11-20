package com.hucet.clean.gallery.presenter

import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.repository.GalleryRepository
import com.hucet.clean.gallery.scheduler.DefaultSchedulerProvider
import com.hucet.clean.gallery.scheduler.SchedulerProvider
import timber.log.Timber

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryPresenter constructor(private val view: Gallery.View,
                                   private val fragment: ListGalleryFragment,
                                   private val repository: GalleryRepository,
                                   private val transformer: MediumTransformer,
                                   private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()


) : Gallery.Presenter {
    override fun fetchItems(curPath: String, readOnlyConfigs: ReadOnlyConfigs) {
        repository
                .getGalleries(curPath, readOnlyConfigs)
                .map {
                    transformer.transform(it, curPath, readOnlyConfigs)
                }
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
                            Timber.d("subscribe ${next}")
                            fragment.getCurrentAdapter()?.updateData(next)
                        },
                        { error ->
                            error.printStackTrace()
                            Timber.d("Error[${error}]")
                            view.showError()
                        },
                        {
                            Timber.d("Completed")
                        })
    }

}