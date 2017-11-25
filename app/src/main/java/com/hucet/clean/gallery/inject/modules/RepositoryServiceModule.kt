package org.buffer.android.boilerplate.ui.injection.module

import android.content.Context
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.datasource.local.NoMediaFolderProvider
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.gallery.filter.OrchestraFilter
import com.hucet.clean.gallery.inject.scopes.PerFragment
import com.hucet.clean.gallery.repository.GalleryRepository
import dagger.Module
import dagger.Provides


/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
class RepositoryServiceModule {
    @Provides
    @PerFragment
    fun provideGalleryRepository(localDataSource: LocalDataSource, transformer: MediumTransformer, imageVideoGifFilter: ImageVideoGifFilter): GalleryRepository {
        return GalleryRepository(localDataSource, transformer, imageVideoGifFilter)
    }

    @Provides
    @PerFragment
    fun provideOrderedFilterContext(filters: Set<@JvmSuppressWildcards MediaTypeFilter>): OrchestraFilter {
        return OrchestraFilter(filters)
    }

    @Provides
    @PerFragment
    fun provideMediaFetcher(context: Context, orchestraFilter: OrchestraFilter): MediaFetcher {
        return MediaFetcher(context, orchestraFilter)
    }

    @Provides
    @PerFragment
    fun provideLocalDataSource(mediaFetcher: MediaFetcher, noMediaFolderProvider: NoMediaFolderProvider): LocalDataSource {
        return LocalDataSource(mediaFetcher, noMediaFolderProvider)
    }
}
