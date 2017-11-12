package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.adapter.DirectoryDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.MediumDelegateAdapter
import com.hucet.clean.gallery.gallery.adapter.ViewTypeDelegateAdapter
import com.hucet.clean.gallery.gallery.list.GlideRequests
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-09.
 */
@Module
class AdapterDelegationModule {
    //    @Binds
//    @IntoMap
//    @GalleryEnumKey(GalleryAdapter.GalleryType.DIRECTORY)
//    abstract fun bindDirectoryViewHolderCreator(conCreate: DirectoryDelegateAdapter)
//            : AbstractDelegateAdapter
//
//    @Binds
//    @IntoMap
//    @GalleryEnumKey(GalleryAdapter.GalleryType.MEDIUM)
//    abstract fun bindMediumViewHolderCreator(conCreate: MediumDelegateAdapter)
//            : AbstractDelegateAdapter
//
//    @Documented
//    @Target(ElementType.METHOD)
//    @Retention(RetentionPolicy.RUNTIME)
//    @MapKey
//    annotation class GalleryEnumKey(val value: GalleryAdapter.GalleryType)
    @Provides
    @PerFragment
    fun provideDirectoryDelegateAdapter(glideRequests: GlideRequests): DirectoryDelegateAdapter {
        return DirectoryDelegateAdapter(glideRequests)
    }

    @Provides
    @PerFragment
    fun provideMediumDelegateAdapter(glideRequests: GlideRequests): MediumDelegateAdapter {
        return MediumDelegateAdapter(glideRequests)
    }


    @Provides
    @PerFragment
    fun provideViewTypeDelegateApdater(d: DirectoryDelegateAdapter, m: MediumDelegateAdapter): ViewTypeDelegateAdapter {
        return ViewTypeDelegateAdapter(d, m)
    }
}