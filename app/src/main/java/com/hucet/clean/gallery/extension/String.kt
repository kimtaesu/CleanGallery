package com.hucet.clean.gallery.extension

import android.os.Environment
import java.io.File
import java.util.*

/**
 * Created by taesu on 2017-10-30.
 */

fun externalStorageDirectory() = Environment.getExternalStorageDirectory().absolutePath

fun String.isExternalStorageDir() = this == externalStorageDirectory()




fun String.getFilenameFromPath() = substring(lastIndexOf("/") + 1)
