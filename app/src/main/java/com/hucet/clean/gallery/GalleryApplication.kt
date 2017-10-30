package com.hucet.clean.gallery

import android.app.Application
import android.os.StrictMode


/**
 * Created by taesu on 2017-10-30.
 */
class GalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initStrictMode()
    }

    fun initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())
        }
        super.onCreate()
    }
}