package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.directory.DirectoryContext
import com.hucet.clean.gallery.inject.scopes.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-26.
 */
@Module
class DirectoryModule {

    @Provides
    @PerActivity
    fun provideDirecotryContext(): DirectoryContext {
        return DirectoryContext()
    }
}