package com.hucet.clean.gallery.extension

import android.content.Context
import android.os.Build
import android.os.Looper

/**
 * Created by taesu on 2017-10-30.
 */
/**
 * Android Thread
 */
fun Context.isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

/**
 * Android Sdk
 */
fun Context.isAndroidFour() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH

fun Context.isKitkatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
fun Context.isLollipopPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun Context.isMarshmallowPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
fun Context.isNougatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N