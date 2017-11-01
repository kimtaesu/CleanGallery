package com.hucet.clean.gallery.inject.component

import com.hucet.clean.gallery.activity.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by taesu on 2017-11-01.
 */
@Subcomponent
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()

}