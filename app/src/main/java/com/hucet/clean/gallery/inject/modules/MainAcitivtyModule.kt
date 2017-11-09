package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.buffer.android.boilerplate.ui.injection.scopes.PerActivity

/**
 * Created by taesu on 2017-10-30.
 */
@Module
abstract class MainAcitivtyModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentProviderModule::class))
    abstract fun bindMainActivity(): MainActivity
}