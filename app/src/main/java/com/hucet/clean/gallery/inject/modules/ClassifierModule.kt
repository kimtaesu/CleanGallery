package com.hucet.clean.gallery.inject.modules

import android.content.Context
import com.hucet.clean.gallery.gallery.category.DateClassifier
import com.hucet.clean.gallery.gallery.category.DirClassifier
import com.hucet.clean.gallery.gallery.category.MediumTransformer
import com.hucet.clean.gallery.inject.scopes.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by taesu on 2017-11-10.
 */
@Module
class ClassifierModule {
    @Provides
    @PerActivity
    fun provideDateClassifier(context: Context): DateClassifier {
        return DateClassifier(context)
    }


    @Provides
    @PerActivity
    fun provideDirClassifier(): DirClassifier {
        return DirClassifier()
    }

    @Provides
    @PerActivity
    fun provideMediumTransformer(date: DateClassifier, dir: DirClassifier): MediumTransformer {
        return MediumTransformer(date, dir)
    }

}