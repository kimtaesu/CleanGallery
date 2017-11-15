package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.fragment.GlideApp
import com.hucet.clean.gallery.gallery.fragment.GlideRequests
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-10-30.
 */
@Module
class ListGalleryFragmentModule {
    @Provides
    @PerFragment
    fun provideGlideRequests(listGalleryFragment: ListGalleryFragment): GlideRequests {
        return GlideApp.with(listGalleryFragment)
    }
}