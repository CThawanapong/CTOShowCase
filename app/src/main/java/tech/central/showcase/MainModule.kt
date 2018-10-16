package tech.central.showcase

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.di.qualifier.ActivityContext
import tech.central.showcase.photo.usecase.LoadPhotoUseCase

@Module
class MainModule {
    @Provides
    @ActivityContext
    fun provideContext(mainActivity: MainActivity): Context = mainActivity

    @Provides
    fun provideMainViewModelFactory(
            application: Application,
            loadPhotoUseCase: LoadPhotoUseCase,
            schedulersFacade: SchedulersFacade
    ) = MainViewModelFactory(application, loadPhotoUseCase, schedulersFacade)
}