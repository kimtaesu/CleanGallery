package com.hucet.clean.gallery.scheduler

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler


/**
 * Created by taesu on 2017-11-01.
 */
class TestSchedulerProvider(val testScheduler: TestScheduler) : SchedulerProvider {

    override fun main() = testScheduler

    override fun computation() = testScheduler

    override fun io(): Scheduler = testScheduler
}
