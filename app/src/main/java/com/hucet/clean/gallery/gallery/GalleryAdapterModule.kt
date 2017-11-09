package com.hucet.clean.gallery.gallery

import com.hucet.clean.gallery.gallery.list.GalleryAdapter
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Created by taesu on 2017-11-09.
 */
@Module
abstract class GalleryAdapterModule {
    @Binds
    @IntoMap
    @StringKey("A")
//    @GalleryEnumKey(GalleryAdapter.GalleryType.DIRECTORY)
    abstract fun provideDirectoryCreator(conCreate: C)
            : A

    @Binds
    @IntoMap
    @StringKey("B")
//    @GalleryEnumKey(GalleryAdapter.GalleryType.MEDIUM)
    abstract fun provideMediumEnumCreator(conCreate: B)
            : A

    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    annotation class GalleryEnumKey(val value: GalleryAdapter.GalleryType)
}