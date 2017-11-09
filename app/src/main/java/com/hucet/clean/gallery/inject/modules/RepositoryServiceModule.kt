package org.buffer.android.boilerplate.ui.injection.module

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.repository.GalleryRepository
import dagger.Module
import dagger.Provides


/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
class RepositoryServiceModule {
    @Provides
    fun provideGalleryRepository(localDataSource: LocalDataSource): GalleryRepository {
        return GalleryRepository(localDataSource)
    }

    @Provides
    fun provideLocalDataSource(context: Context, appConfig: ApplicationConfig): LocalDataSource {
        return LocalDataSource(MediaFetcher(context, appConfig))
    }

}
