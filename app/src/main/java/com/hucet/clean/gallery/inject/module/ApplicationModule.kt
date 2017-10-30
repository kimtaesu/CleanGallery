package org.buffer.android.boilerplate.ui.injection.module

import android.app.Application
import android.content.Context
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
}
