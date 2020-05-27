package tech.central.showcase

import android.content.Context
import dagger.Module
import dagger.Provides
import tech.central.showcase.di.qualifier.ActivityContext

@Module
class MainModule {
    @Provides
    @ActivityContext
    fun provideContext(mainActivity: MainActivity): Context = mainActivity
}