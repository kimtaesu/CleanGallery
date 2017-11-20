package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.gallery.fragment.ViewModeType
import com.hucet.clean.gallery.gallery.fragment.switchable.GridViewModeSetUp
import com.hucet.clean.gallery.gallery.fragment.switchable.LinearViewModeSetUp
import com.hucet.clean.gallery.gallery.fragment.switchable.ViewModeSwichable
import com.hucet.clean.gallery.inject.scopes.PerFragment
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import java.lang.annotation.Documented

/**
 * Created by taesu on 2017-11-20.
 */
@Module
abstract class ViewModeSetUpModule {
    @Binds
    @IntoMap
    @PerFragment
    @ViewModeEnumKey(ViewModeType.GRID)
    abstract fun bindGridViewModeSetUp(conCreate: GridViewModeSetUp)
            : ViewModeSwichable


    @Binds
    @IntoMap
    @PerFragment
    @ViewModeEnumKey(ViewModeType.LINEAR)
    abstract fun bindLinearViewModeSetUp(conCreate: LinearViewModeSetUp)
            : ViewModeSwichable


    @Documented
    @MapKey
    annotation class ViewModeEnumKey(val value: ViewModeType)
}