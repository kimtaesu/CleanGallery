package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.adapter.*
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

/**
 * Created by taesu on 2017-11-09.
 */
@Module
abstract class AdapterDelegationModule {
    @Binds
    @IntoMap
    @PerFragment
    @GalleryEnumKey(GalleryType.DIRECTORY)
    abstract fun bindDirectoryViewHolderCreator(conCreate: DirectoryDelegateAdapter)
            : AbstractDelegateAdapter

    @Binds
    @IntoMap
    @PerFragment
    @GalleryEnumKey(GalleryType.MEDIUM)
    abstract fun bindMediumViewHolderCreator(conCreate: MediumDelegateAdapter)
            : AbstractDelegateAdapter

    @Documented
    @MapKey
    annotation class GalleryEnumKey(val value: GalleryType)
}