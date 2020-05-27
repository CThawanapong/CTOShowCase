package tech.central.showcase.base

import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class SchedulersFacade {
    open val io: Scheduler
        get() = Schedulers.io()

    open val computation: Scheduler
        get() = Schedulers.computation()

    open val ui: Scheduler
        get() = AndroidSchedulers.from(Looper.getMainLooper(), true)
}