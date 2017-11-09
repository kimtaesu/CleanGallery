package org.buffer.android.boilerplate.ui.injection.module

import android.app.Application
import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import dagger.Module
import dagger.Provides
import org.buffer.android.boilerplate.ui.injection.scopes.PerApplication

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {
    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    fun provideConfig(application: Application): ApplicationConfig {
        return ApplicationConfig(application)
    }
}
