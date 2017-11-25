package com.hucet.clean.gallery.debug

import android.util.Log
import com.google.firebase.crash.FirebaseCrash
import java.lang.reflect.Array.setInt
import timber.log.Timber


/**
 * Created by taesu on 2017-11-25.
 */
class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String?, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        val t = throwable ?: Exception(message)

        // Firebase Crash Reporting
        FirebaseCrash.logcat(priority, tag, message)
        FirebaseCrash.report(t)
    }

    companion object {
        private val CRASHLYTICS_KEY_PRIORITY = "priority"
        private val CRASHLYTICS_KEY_TAG = "tag"
        private val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}