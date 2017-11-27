package org.buffer.android.boilerplate.ui.injection.module

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.ReadOnlyApplicationConfig
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.datasource.local.NoMediaFolderProvider
import com.hucet.clean.gallery.gallery.filter.MediaTypeFilter
import com.hucet.clean.gallery.gallery.filter.OrchestraFilter
import com.hucet.clean.gallery.inject.scopes.PerActivity
import com.hucet.clean.gallery.repository.GalleryRepository
import dagger.Module
import dagger.Provides


/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
class RepositoryServiceModule {
    @Provides
    @PerActivity
    fun provideGalleryRepository(localDataSource: LocalDataSource): GalleryRepository {
        return GalleryRepository(localDataSource)
    }

    @Provides
    @PerActivity
    fun provideOrderedFilterContext(filters: Set<@JvmSuppressWildcards MediaTypeFilter>,
                                    config: ReadOnlyApplicationConfig): OrchestraFilter {
        return OrchestraFilter(filters, config)
    }

    @Provides
    @PerActivity
    fun provideMediaFetcher(context: Context, orchestraFilter: OrchestraFilter): MediaFetcher {
        return MediaFetcher(context, orchestraFilter)
    }

    @Provides
    @PerActivity
    fun provideLocalDataSource(mediaFetcher: MediaFetcher, noMediaFolderProvider: NoMediaFolderProvider): LocalDataSource {
        return LocalDataSource(mediaFetcher, noMediaFolderProvider)
    }
}
