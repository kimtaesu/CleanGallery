package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.adapter.AbstractGalleryCreator
import com.hucet.clean.gallery.gallery.adapter.DirectoryViewHolderCreator
import com.hucet.clean.gallery.gallery.adapter.MediumViewHolderCreator
import com.hucet.clean.gallery.gallery.adapter.GalleryAdapter
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

/**
 * Created by taesu on 2017-11-09.
 */
@Module
abstract class AdapterDelegationModule {
    @Binds
    @IntoMap
    @GalleryEnumKey(GalleryAdapter.GalleryType.DIRECTORY)
    abstract fun bindDirectoryViewHolderCreator(conCreate: DirectoryViewHolderCreator)
            : AbstractGalleryCreator

    @Binds
    @IntoMap
    @GalleryEnumKey(GalleryAdapter.GalleryType.MEDIUM)
    abstract fun bindMediumViewHolderCreator(conCreate: MediumViewHolderCreator)
            : AbstractGalleryCreator

    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    annotation class GalleryEnumKey(val value: GalleryAdapter.GalleryType)
}