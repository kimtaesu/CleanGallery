package org.buffer.android.boilerplate.ui.injection.module

import android.content.Context
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.datasource.local.LocalDataSource
import com.hucet.clean.gallery.datasource.local.MediaFetcher
import com.hucet.clean.gallery.repository.GalleryRepository
import dagger.Module
import dagger.Provides
import org.buffer.android.boilerplate.ui.injection.scopes.PerActivity


/**
 * Module used to provide dependencies at an activity-level.
 */
@Module
class MainActivityModule {
    @Provides
    @PerActivity
    fun provideGalleryRepository(localDataSource: LocalDataSource): GalleryRepository {
        return GalleryRepository(localDataSource)
    }

    @Provides
    @PerActivity
    fun provideLocalDataSource(context: Context, appConfig: ApplicationConfig): LocalDataSource {
        return LocalDataSource(MediaFetcher(context, appConfig))
    }

}
