package tech.central.showcase.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import tech.central.showcase.di.qualifier.ApplicationContext
import java.util.*
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    @ApplicationContext
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideLocale() = Locale.getDefault()
}