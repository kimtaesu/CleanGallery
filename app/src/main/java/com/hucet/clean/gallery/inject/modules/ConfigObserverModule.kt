package com.hucet.clean.gallery.inject.modules

import com.hucet.clean.gallery.activity.MainActivity
import com.hucet.clean.gallery.config.ApplicationConfig
import com.hucet.clean.gallery.config.ConfigLogic
import com.hucet.clean.gallery.config.ConfigOrderedNotifier
import com.hucet.clean.gallery.config.OnConfigObserver
import com.hucet.clean.gallery.gallery.directory.PathLocationContext
import com.hucet.clean.gallery.inject.scopes.PerActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

/**
 * Created by taesu on 2017-11-27.
 */
@Module
class ConfigObserverModule {

    @Provides
    @PerActivity
    fun provideConfigChangedNotifier(observers: Set<@JvmSuppressWildcards OnConfigObserver>): ConfigOrderedNotifier {
        return ConfigOrderedNotifier(observers)
    }

    @Provides
    @PerActivity
    fun provideConfigLogic(config: ApplicationConfig): ConfigLogic {
        return ConfigLogic(config)
    }

    @Provides
    @PerActivity
    @IntoSet
    fun provideOnConfigObserver1(configLogic: ConfigLogic): OnConfigObserver {
        return configLogic
    }

    @Provides
    @PerActivity
    @IntoSet
    fun OnConfigObserve2(pathLocationContext: PathLocationContext): OnConfigObserver {
        return pathLocationContext
    }

    @Provides
    @PerActivity
    @IntoSet
    fun OnConfigObserve3(mainActivity: MainActivity): OnConfigObserver {
        return mainActivity
    }
}