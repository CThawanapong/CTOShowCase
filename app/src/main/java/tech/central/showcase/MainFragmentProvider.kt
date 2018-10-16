package tech.central.showcase

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tech.central.showcase.dashboard.DashboardFragment
import tech.central.showcase.di.scope.PerFragment
import tech.central.showcase.photo.PhotoFragment
import tech.central.showcase.photo.PhotoModule

@Module
abstract class MainFragmentProvider {
    @PerFragment
    @ContributesAndroidInjector(modules = [])
    abstract fun bindDashboardFragment(): DashboardFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PhotoModule::class])
    abstract fun bindPhotoFragment(): PhotoFragment
}