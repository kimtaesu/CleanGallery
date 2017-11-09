package com.hucet.clean.gallery.inject

import android.app.Application
import com.hucet.clean.gallery.GalleryApplication
import com.hucet.clean.gallery.inject.binder.ActivityBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import org.buffer.android.boilerplate.ui.injection.module.ApplicationModule
import org.buffer.android.boilerplate.ui.injection.scopes.PerApplication

/**
 * Created by taesu on 2017-10-30.
 */
@PerApplication
@Component(modules = arrayOf(
        ActivityBindingModule::class,
        ApplicationModule::class,
        AndroidSupportInjectionModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: GalleryApplication)
}
