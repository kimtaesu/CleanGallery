package com.hucet.clean.gallery.inject.modules

import android.content.Context
import com.hucet.clean.gallery.gallery.category.DateTransformer
import com.hucet.clean.gallery.gallery.category.DirTransformer
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
    fun provideDateClassifier(context: Context): DateTransformer {
        return DateTransformer(context)
    }


    @Provides
    @PerActivity
    fun provideDirClassifier(): DirTransformer {
        return DirTransformer()
    }
}