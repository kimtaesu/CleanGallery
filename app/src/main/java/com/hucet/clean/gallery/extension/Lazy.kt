package com.hucet.clean.gallery.extension

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * Created by taesu on 2017-11-27.
 */
fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}
