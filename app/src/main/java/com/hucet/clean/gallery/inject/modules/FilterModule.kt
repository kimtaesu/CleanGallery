package com.hucet.clean.gallery.inject.modules

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-13.
 */
@Module
class FilterModule {
    @Provides
    @PerFragment
    fun provideMediaTypeFilter(appConfig: ApplicationConfig): MediaTypeFilter {
        return MediaTypeFilter(appConfig)
    }
}