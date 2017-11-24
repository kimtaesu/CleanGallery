package com.hucet.clean.gallery.extension

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import java.io.File
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by taesu on 2017-10-30.
 */

fun getExternalStorageDirectory(): () -> String {
    return { Environment.getExternalStorageDirectory().absolutePath.trimEnd('/') }
}
