package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.config.DAGGER_NAMED_GRID
import com.hucet.clean.gallery.config.DAGGER_NAMED_LINEAR
import com.hucet.clean.gallery.gallery.adapter.AbstractDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.GalleryType
import com.hucet.clean.gallery.gallery.adapter.grid.DateGridDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.grid.DirectoryGridDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.grid.MediumGridDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.linear.DirectoryLinearDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.linear.MediumLinearDelegateAdapter
import com.hucet.clean.gallery.inject.scopes.PerActivity
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
    @PerActivity
    @GalleryEnumKey(GalleryType.DIRECTORY)
    @Named(DAGGER_NAMED_LINEAR)
    abstract fun bindDirectoryLinearViewHolderCreator(conCreate: DirectoryLinearDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerActivity
    @GalleryEnumKey(GalleryType.MEDIUM)
    @Named(DAGGER_NAMED_LINEAR)
    abstract fun bindMediumLinearViewHolderCreator(conCreate: MediumLinearDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerActivity
    @GalleryEnumKey(GalleryType.DIRECTORY)
    @Named(DAGGER_NAMED_GRID)
    abstract fun bindDirectoryGridViewHolderCreator(conCreate: DirectoryGridDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerActivity
    @GalleryEnumKey(GalleryType.DATE)
    @Named(DAGGER_NAMED_GRID)
    abstract fun bindDateDelegateAdapter(conCreate: DateGridDelegateAdapter)
            : AbstractDelegateAdapter


    @Binds
    @IntoMap
    @PerActivity
    @GalleryEnumKey(GalleryType.MEDIUM)
    @Named(DAGGER_NAMED_GRID)
    abstract fun bindMediumGridViewHolderCreator(conCreate: MediumGridDelegateAdapter)
            : AbstractDelegateAdapter


    @Documented
    @MapKey
    annotation class GalleryEnumKey(val value: GalleryType)
}