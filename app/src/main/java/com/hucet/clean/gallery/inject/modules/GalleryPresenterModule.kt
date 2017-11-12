package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.list.ListGalleryFragment
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
    fun providesGalleryPresenter(view: Gallery.View, adapter: GalleryAdapter, repository: GalleryRepository, transformer: MediumTransformer): Gallery.Presenter {
        return GalleryPresenter(view, adapter, repository, transformer)
    }

    @Provides
    @PerFragment
    fun providesGalleryFragmentView(fragment: ListGalleryFragment): Gallery.View {
        return fragment
    }
}
