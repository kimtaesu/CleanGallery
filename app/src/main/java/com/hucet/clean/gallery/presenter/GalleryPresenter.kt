package com.hucet.clean.gallery.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.hucet.clean.gallery.config.ReadOnlyConfigs
import com.hucet.clean.gallery.extension.isExternalStorageDir
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.repository.GalleryRepository
import com.hucet.clean.gallery.scheduler.DefaultSchedulerProvider
import com.hucet.clean.gallery.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryPresenter constructor(private val view: Gallery.View,
                                   private val fragment: ListGalleryFragment,
                                   private val repository: GalleryRepository,
                                   private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : Gallery.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun fetchItems(curPath: String, isRoot: Boolean, readOnlyConfigs: ReadOnlyConfigs, cacheInvalidate: Boolean) {
        repository
                .getGalleries(readOnlyConfigs, curPath, isRoot, cacheInvalidate)
                .map {
                    println("calculateDiff")
                    Timber.d("calculateDiff")
                    fragment.getCurrentAdapter()?.calculateDiff(it)!!
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
                            fragment.getCurrentAdapter()?.updateByDiff(next)
                        },
                        { error ->
                            error.printStackTrace()
                            Timber.d("Error[${error}]")
                            view.showError()
                        },
                        {
                            Timber.d("Completed")
                        })
                .also { compositeDisposable.add(it) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun clear() {
        compositeDisposable.clear()
    }
}