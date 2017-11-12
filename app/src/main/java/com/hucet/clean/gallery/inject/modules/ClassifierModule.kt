package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.gallery.category.DateClassifier
import com.hucet.clean.gallery.gallery.category.DirClassifier
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-10.
 */
@Module
class ClassifierModule {
    @Provides
    @PerFragment
    fun provideDateClassifier(applicationConfig: ApplicationConfig): DateClassifier {
        return DateClassifier(applicationConfig)
    }


    @Provides
    @PerFragment
    fun provideDirClassifier(): DirClassifier {
        return DirClassifier()
    }

    @Provides
    @PerFragment
    fun provideMediumTransformer(date: DateClassifier, dir: DirClassifier): MediumTransformer {
        return MediumTransformer(date, dir)
    }

}