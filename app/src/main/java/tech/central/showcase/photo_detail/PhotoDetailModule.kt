package tech.central.showcase.photo_detail

import android.app.Application
import dagger.Module
import dagger.Provides
import tech.central.showcase.base.SchedulersFacade

@Module
class PhotoDetailModule {
    @Provides
    fun providePhotoDetailViewModelFactory(
            application: Application,
            schedulersFacade: SchedulersFacade
    ) = PhotoDetailViewModelFactory(application, schedulersFacade)
}