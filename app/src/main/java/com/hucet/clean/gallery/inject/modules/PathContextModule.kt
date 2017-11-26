package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.category.DateTransformer
import com.hucet.clean.gallery.gallery.category.DirTransformer
import com.hucet.clean.gallery.gallery.directory.DirectoryRootChecker
import com.hucet.clean.gallery.gallery.directory.PathLocationContext
import com.hucet.clean.gallery.gallery.directory.PathLocationContext.*
import com.hucet.clean.gallery.gallery.directory.SubjectMapper
import com.hucet.clean.gallery.gallery.filter.ImageVideoGifFilter
import com.hucet.clean.gallery.inject.scopes.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-26.
 */

@Module
class PathContextModule() {
    @Provides
    @PerActivity
    fun providePathLocationContext(
            config: ApplicationConfig,
            imageVideoGifFilter: ImageVideoGifFilter,
            dirTransformer: DirTransformer,
            dateTransformer: DateTransformer
    ): PathLocationContext {
        val mappers = mapOf(DirectoryType.ROOT to SubjectMapper.DirecotryRootMapper(dirTransformer),
                DirectoryType.MEDIUM to SubjectMapper.DirectoryMediumMapper(),
                DirectoryType.DATE to SubjectMapper.DateMediumMapper(dateTransformer))
        return PathLocationContext(imageVideoGifFilter, config, mappers)
    }
}