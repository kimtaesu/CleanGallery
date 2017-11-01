package org.buffer.android.boilerplate.ui.injection.module

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
    fun provideGalleryRepository(): GalleryRepository {
        return GalleryRepository()
    }
}
