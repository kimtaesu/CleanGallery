package com.hucet.clean.gallery.gallery.list

import com.hucet.clean.gallery.gallery.list.presenter.Gallery
import com.hucet.clean.gallery.gallery.list.presenter.GalleryPresenter
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.repository.GalleryRepository
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-01.
 */
@Module
class ListGalleryFragmentModule {

    @PerFragment
    @Provides
    fun providesGalleryPresenter(view: Gallery.View, adapter: GalleryAdapter, repository: GalleryRepository): Gallery.Presenter {
        return GalleryPresenter(view, adapter, repository)
    }

    @PerFragment
    @Provides
    fun providesGalleryFragmentView(fragment: ListGalleryFragment): Gallery.View {
        return fragment
    }

    @PerFragment
    @Provides
    fun providesGlideRequests(fragment: ListGalleryFragment): GlideRequests {
        return GlideApp.with(fragment)
    }
}
