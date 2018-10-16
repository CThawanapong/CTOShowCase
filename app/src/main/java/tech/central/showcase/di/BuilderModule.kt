package tech.central.showcase.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tech.central.showcase.MainActivity
import tech.central.showcase.MainFragmentProvider
import tech.central.showcase.MainModule
import tech.central.showcase.di.scope.PerActivity

@Module
abstract class BuilderModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}