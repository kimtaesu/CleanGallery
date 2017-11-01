package com.hucet.clean.gallery.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by taesu on 2017-11-01.
 */
interface SchedulerProvider {
    fun computation(): Scheduler

    fun io(): Scheduler

    fun main(): Scheduler
}

class DefaultSchedulerProvider : SchedulerProvider {
    override fun io() = Schedulers.io()

    override fun computation() = Schedulers.computation()

    override fun main() = AndroidSchedulers.mainThread()
}