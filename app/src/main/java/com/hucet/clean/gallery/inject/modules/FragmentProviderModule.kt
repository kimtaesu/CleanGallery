package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.fragment.GalleryDetailFragment
import com.hucet.clean.gallery.gallery.fragment.ListGalleryFragment
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.buffer.android.boilerplate.ui.injection.module.RepositoryServiceModule

/**
 * Created by taesu on 2017-11-01.
 */
@Module(includes = arrayOf())
abstract class FragmentProviderModule {
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(
            AdapterDelegationModule::class,
            ViewModeSetUpModule::class,
            GalleryPresenterModule::class,
            ClassifierModule::class,
            ListGalleryFragmentModule::class,
            RepositoryServiceModule::class,
            FilterModule::class
    ))
    abstract fun bindGalleryFragment(): ListGalleryFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindGalleryDetailFragment(): GalleryDetailFragment
}
