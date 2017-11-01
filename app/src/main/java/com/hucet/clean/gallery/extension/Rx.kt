package com.hucet.clean.gallery.extension

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by taesu on 2017-11-01.
 */
inline fun <T> Flowable<T>.io() = observeOn(Schedulers.io())

inline fun <T> Flowable<T>.computation() = observeOn(Schedulers.computation())
inline fun <T> Flowable<T>.newThread() = observeOn(Schedulers.newThread())
inline fun <T> Flowable<T>.main() = observeOn(AndroidSchedulers.mainThread())