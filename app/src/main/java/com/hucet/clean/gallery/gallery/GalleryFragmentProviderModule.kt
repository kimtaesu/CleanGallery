package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragmentModule
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by taesu on 2017-11-01.
 */
@Module
abstract class GalleryFragmentProviderModule {
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(ListGalleryFragmentModule::class))
    abstract fun bindGalleryFragment(): ListGalleryFragment
}
