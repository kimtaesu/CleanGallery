package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.inject.scopes.PerActivity
import com.hucet.clean.gallery.presenter.Gallery
import com.hucet.clean.gallery.presenter.GalleryPresenter
import com.hucet.clean.gallery.repository.GalleryRepository
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-01.
 */
@Module
class GalleryPresenterModule {
    @Provides
    @PerActivity
    fun providesGalleryPresenter(view: Gallery.View, activity: MainActivity, repository: GalleryRepository): Gallery.Presenter {
        return GalleryPresenter(view, activity, repository)
    }

    @Provides
    @PerActivity
    fun providesGalleryGalleryView(activity: MainActivity): Gallery.View {
        return activity
    }
}
