package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.adapter.*
import com.hucet.clean.gallery.gallery.list.GlideApp
import com.hucet.clean.gallery.gallery.list.GlideRequests
import com.hucet.clean.gallery.gallery.list.ListGalleryFragment
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

    @Provides
    @PerFragment
    fun provideViewTypeDelegateApdater(d: DirectoryDelegateAdapter, m: MediumDelegateAdapter): ViewTypeDelegateAdapter {
        return ViewTypeDelegateAdapter(d, m)
    }

    @Provides
    @PerFragment
    fun provideDirectoryDelegateAdapter(glideRequests: GlideRequests): DirectoryDelegateAdapter {
        return DirectoryDelegateAdapter(glideRequests)
    }

    @Provides
    @PerFragment
    fun provideMediumDelegateAdapter(glideRequests: GlideRequests): MediumDelegateAdapter {
        return MediumDelegateAdapter(glideRequests)
    }
}