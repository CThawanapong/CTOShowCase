package tech.central.showcase.base

import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SchedulersFacade @Inject constructor() {
    val io: Scheduler
        get() = Schedulers.io()

    val computation: Scheduler
        get() = Schedulers.computation()

    val ui: Scheduler
        get() = AndroidSchedulers.from(Looper.getMainLooper(), true)
}