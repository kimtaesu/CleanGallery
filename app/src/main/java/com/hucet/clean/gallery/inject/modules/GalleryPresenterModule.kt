package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.inject.scopes.PerFragment
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
    @PerFragment
    fun providesGalleryPresenter(view: Gallery.View, fragment: ListGalleryFragment, repository: GalleryRepository): Gallery.Presenter {
        return GalleryPresenter(view, fragment, repository)
    }

    @Provides
    @PerFragment
    fun providesGalleryFragmentView(fragment: ListGalleryFragment): Gallery.View {
        return fragment
    }
}
