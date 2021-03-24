package com.android.propertymatch.modules

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun provideScheduler() : Scheduler
}

class MainSchedulerProvider : SchedulerProvider {
    override fun provideScheduler() : Scheduler {
        return AndroidSchedulers.mainThread()
    }
}

class IoSchedulerProvider : SchedulerProvider {
    override fun provideScheduler() : Scheduler {
        return Schedulers.io()
    }
}