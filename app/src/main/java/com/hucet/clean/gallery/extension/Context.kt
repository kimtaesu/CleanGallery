package com.hucet.clean.gallery.extension

import android.os.Build
import android.os.Looper

/**
 * Created by taesu on 2017-10-30.
 */
/**
 * Android Thread
 */
fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

/**
 * Android Sdk
 */
fun isAndroidFour() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH

fun isKitkatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
fun isLollipopPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun isMarshmallowPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
fun isNougatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N