package org.buffer.android.boilerplate.ui.injection.module

import android.content.Context
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.datasource.local.NoMediaFolderProvider
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.gallery.filter.OrderedFilterContext
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
    fun provideGalleryRepository(localDataSource: LocalDataSource): GalleryRepository {
        return GalleryRepository(localDataSource)
    }

    @Provides
    @PerFragment
    fun provideOrderedFilterContext(filters: Set<@JvmSuppressWildcards MediaTypeFilter>): OrderedFilterContext {
        return OrderedFilterContext(filters)
    }

    @Provides
    @PerFragment
    fun provideMediaFetcher(context: Context): MediaFetcher {
        return MediaFetcher(context)
    }

    @Provides
    @PerFragment
    fun provideLocalDataSource(mediaFetcher: MediaFetcher): LocalDataSource {
        return LocalDataSource(mediaFetcher)
    }
}
