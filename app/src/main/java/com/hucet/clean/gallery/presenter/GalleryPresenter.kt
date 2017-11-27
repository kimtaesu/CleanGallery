package com.hucet.clean.gallery.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.gallery.directory.PathLocationContext
import com.hucet.clean.gallery.repository.GalleryRepository
import com.hucet.clean.gallery.scheduler.DefaultSchedulerProvider
import com.hucet.clean.gallery.scheduler.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by taesu on 2017-10-31.
 */
class GalleryPresenter constructor(private val view: Gallery.View,
                                   private val activity: MainActivity,
                                   private val repository: GalleryRepository,
                                   private val schedulerProvider: SchedulerProvider = DefaultSchedulerProvider()
) : Gallery.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun fetchItems(pathLocationContext: PathLocationContext, cacheInvalidate: Boolean) {
        repository
                .getGalleries(pathLocationContext, cacheInvalidate)
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
                            val adapter = activity.getCurrentAdapter() ?: throw NullPointerException("adapter is null")
                            val diffUtil = adapter.calculateDiff(next)
                            adapter.updateByDiff(diffUtil)
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