package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.inject.scopes.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.buffer.android.boilerplate.ui.injection.module.RepositoryServiceModule

/**
 * Created by taesu on 2017-10-30.
 */
@Module
abstract class MainAcitivtyModule {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(
            AdapterDelegationModule::class,
            ViewModeSetUpModule::class,
            GalleryPresenterModule::class,
            ClassifierModule::class,
            RepositoryServiceModule::class,
            FilterModule::class
    ))
    abstract fun bindMainActivity(): MainActivity
}