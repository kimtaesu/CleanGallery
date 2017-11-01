package com.hucet.clean.gallery.gallery.fragment

import com.hucet.clean.gallery.gallery.Gallery
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-01.
 */
@Module
class ListGalleryFragmentModule {

    @PerFragment
    @Provides
    internal fun providesGalleryFragmentView(fragment: ListGalleryFragment): Gallery.View {
        return fragment
    }
//
//    @PerFragment
//    @Provides
//    internal fun providesDetailFragmentPresenter(detailFragmentView: DetailFragmentView): DetailFragmentPresenter {
//        return DetailFragmentPresenter(detailFragmentView)
//    }

}
