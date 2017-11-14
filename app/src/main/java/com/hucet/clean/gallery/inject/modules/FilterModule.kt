package com.hucet.clean.gallery.inject.modules

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.gallery.filter.HiddenFileFilter
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

/**
 * Created by taesu on 2017-11-13.
 */
@Module(includes = arrayOf())
abstract class FilterModule {
    @Binds
    @IntoSet
    @PerFragment
    abstract fun provideHiddenFileFilter(config: HiddenFileFilter): MediaTypeFilter

    @Binds
    @IntoSet
    @PerFragment
    abstract fun provideImageVideoGifFilter(config: ImageVideoGifFilter): MediaTypeFilter
}