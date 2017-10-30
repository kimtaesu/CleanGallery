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

fun Context.scanFile(file: File, action: () -> Unit) {
    scanFiles(arrayListOf(file), action)
}

fun Context.scanPath(path: String, action: () -> Unit) {
    scanPaths(arrayListOf(path), action)
}

fun getExternalStorageDirectory() = Environment.getExternalStorageDirectory().absolutePath.trimEnd('/')

fun getSdCardStorageDirectory() = "SD Card"

fun Context.scanFiles(files: ArrayList<File>, action: () -> Unit) {
    val allPaths = ArrayList<String>()
    for (file in files) {
        allPaths.addAll(getPaths(file))
    }
    rescanPaths(allPaths, action)
}

fun Context.scanPaths(paths: ArrayList<String>, action: () -> Unit) {
    val allPaths = ArrayList<String>()
    for (path in paths) {
        allPaths.addAll(getPaths(File(path)))
    }
    rescanPaths(allPaths, action)
}

fun Context.rescanPaths(paths: ArrayList<String>, action: () -> Unit) {
    var cnt = paths.size
    val realAction = WeakReference<() -> Unit>(action)
    MediaScannerConnection.scanFile(applicationContext, paths.toTypedArray(), null, { s, uri ->
        if (--cnt == 0) {
            realAction.get()?.invoke()
        }
    })
}

fun getPaths(file: File): ArrayList<String> {
    val paths = arrayListOf<String>(file.absolutePath)
    if (file.isDirectory) {
        val files = file.listFiles() ?: return paths
        for (curFile in files) {
            paths.addAll(getPaths(curFile))
        }
    }
    return paths
}