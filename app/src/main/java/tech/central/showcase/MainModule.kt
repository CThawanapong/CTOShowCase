package tech.central.showcase

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.di.qualifier.ActivityContext
import tech.central.showcase.photo.usecase.LoadPhotoUseCase
import tech.central.showcase.post.usecase.LoadPostUseCase

@Module
class MainModule {
    @Provides
    @ActivityContext
    fun provideContext(mainActivity: MainActivity): Context = mainActivity

    @Provides
    fun provideMainViewModelFactory(
            application: Application,
            loadPhotoUseCase: LoadPhotoUseCase,
            loadPostUseCase: LoadPostUseCase,
            schedulersFacade: SchedulersFacade
    ) = MainViewModelFactory(application,schedulersFacade, loadPhotoUseCase, loadPostUseCase )
}