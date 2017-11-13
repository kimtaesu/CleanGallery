package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.gallery.list.GlideApp
import com.hucet.clean.gallery.gallery.list.GlideRequests
import com.hucet.clean.gallery.gallery.list.ListGalleryFragment
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import org.buffer.android.boilerplate.ui.injection.scopes.PerActivity

/**
 * Created by taesu on 2017-10-30.
 */
@Module
abstract class MainAcitivtyModule {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(FragmentProviderModule::class))
    abstract fun bindMainActivity(): MainActivity
}