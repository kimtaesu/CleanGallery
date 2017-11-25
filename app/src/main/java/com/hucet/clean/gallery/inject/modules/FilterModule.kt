package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.filter.HiddenFileFilter
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.inject.scopes.PerActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

/**
 * Created by taesu on 2017-11-13.
 */
@Module(includes = arrayOf())
abstract class FilterModule {
    @Binds
    @IntoSet
    @PerActivity
    abstract fun provideHiddenFileFilter(config: HiddenFileFilter): MediaTypeFilter

    @Binds
    @IntoSet
    @PerActivity
    abstract fun provideImageVideoGifFilter(config: ImageVideoGifFilter): MediaTypeFilter
}