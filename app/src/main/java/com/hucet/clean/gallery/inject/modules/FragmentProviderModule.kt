package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.detail.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.list.ListGalleryFragment
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by taesu on 2017-11-01.
 */
@Module(includes = arrayOf())
abstract class FragmentProviderModule {
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(AdapterDelegationModule::class, GalleryPresenterModule::class))
    abstract fun bindGalleryFragment(): ListGalleryFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindGalleryDetailFragment(): GalleryDetailFragment
}
