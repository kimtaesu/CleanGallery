package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.view_mode.ViewModeType
import com.hucet.clean.gallery.gallery.view_mode.GridViewModeSetUp
import com.hucet.clean.gallery.gallery.view_mode.LinearViewModeSetUp
import com.hucet.clean.gallery.gallery.view_mode.ViewModeNavigator
import com.hucet.clean.gallery.gallery.view_mode.ViewModeSwichable
import com.hucet.clean.gallery.inject.scopes.PerActivity
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.Documented

/**
 * Created by taesu on 2017-11-20.
 */
@Module(includes = arrayOf(ViewModeSetUp2Module::class))
abstract class ViewModeSetUpModule {
    @Binds
    @IntoMap
    @PerActivity
    @ViewModeEnumKey(ViewModeType.GRID)
    abstract fun bindGridViewModeSetUp(conCreate: GridViewModeSetUp)
            : ViewModeSwichable


    @Binds
    @IntoMap
    @PerActivity
    @ViewModeEnumKey(ViewModeType.LINEAR)
    abstract fun bindLinearViewModeSetUp(conCreate: LinearViewModeSetUp)
            : ViewModeSwichable


    @Documented
    @MapKey
    annotation class ViewModeEnumKey(val value: ViewModeType)
}

@Module
class ViewModeSetUp2Module {
    @Provides
    @PerActivity
    fun provideViewModeNavigator(mapViewModeSetUp: Map<ViewModeType, @JvmSuppressWildcards ViewModeSwichable>): ViewModeNavigator {
        return ViewModeNavigator(mapViewModeSetUp)
    }
}