package org.buffer.android.boilerplate.ui.injection.module

import android.app.Application
import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module used to provide dependencies at an application-level.
 */
@Module(includes = arrayOf())
open class ApplicationModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideConfig(application: Application): ApplicationConfig = ApplicationConfig(application)
}
