package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.gallery.detail.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.detail.GalleryDetailFragmentModule
import com.hucet.clean.gallery.gallery.list.ListGalleryFragment
import com.hucet.clean.gallery.gallery.list.ListGalleryFragmentModule
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

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(GalleryDetailFragmentModule::class))
    abstract fun bindGalleryDetailFragment(): GalleryDetailFragment
}
