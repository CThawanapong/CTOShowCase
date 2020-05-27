package tech.central.showcase.di

import android.content.Context
import dagger.Module
import dagger.Provides
import tech.central.showcase.base.BaseTestHolderActivity
import tech.central.showcase.di.qualifier.ActivityContext

@Module
class BaseTestHolderModule {
    @Provides
    @ActivityContext
    fun provideContext(baseTestHolderActivity: BaseTestHolderActivity): Context =
        baseTestHolderActivity
}