package tech.central.showcase.post_detail

import android.app.Application
import dagger.Module
import dagger.Provides
import tech.central.showcase.base.SchedulersFacade

@Module
class PostDetailModule {
    @Provides
    fun providePostDetailViewModelFactory(
            application: Application,
            schedulersFacade: SchedulersFacade
    ) = PostDetailViewModelFactory(application, schedulersFacade)
}