package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.adapter.*
import com.hucet.clean.gallery.gallery.adapter.grid.DateGridDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.grid.DirectoryGridDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.grid.MediumGridDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.linear.DirectoryLinearDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.linear.MediumLinearDelegateAdapter
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import java.lang.annotation.Documented
import javax.inject.Named

/**
 * Created by taesu on 2017-11-09.
 */
@Module
abstract class AdapterDelegationModule {
    @Binds
    @IntoMap
    @PerFragment
    @GalleryEnumKey(GalleryType.DIRECTORY)
    @Named("linear")
    abstract fun bindDirectoryLinearViewHolderCreator(conCreate: DirectoryLinearDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerFragment
    @GalleryEnumKey(GalleryType.MEDIUM)
    @Named("linear")
    abstract fun bindMediumLinearViewHolderCreator(conCreate: MediumLinearDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerFragment
    @GalleryEnumKey(GalleryType.DIRECTORY)
    @Named("grid")
    abstract fun bindDirectoryGridViewHolderCreator(conCreate: DirectoryGridDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerFragment
    @GalleryEnumKey(GalleryType.DATE)
    @Named("grid")
    abstract fun bindDateDelegateAdapter(conCreate: DateGridDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerFragment
    @GalleryEnumKey(GalleryType.MEDIUM)
    @Named("grid")
    abstract fun bindMediumGridViewHolderCreator(conCreate: MediumGridDelegateAdapter)
            : AbstractDelegateAdapter


    @Documented
    @MapKey
    annotation class GalleryEnumKey(val value: GalleryType)
}