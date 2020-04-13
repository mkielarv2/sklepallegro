package com.mkielar.sklepallegro.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class SchedulerProviderImpl : SchedulerProvider {
    override fun io(): Scheduler = io.reactivex.schedulers.Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}