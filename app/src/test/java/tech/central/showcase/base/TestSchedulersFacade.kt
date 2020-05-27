package tech.central.showcase.base

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulersFacade : SchedulersFacade() {
    override val io: Scheduler
        get() = Schedulers.trampoline()

    override val computation: Scheduler
        get() = Schedulers.trampoline()

    override val ui: Scheduler
        get() = Schedulers.trampoline()
}