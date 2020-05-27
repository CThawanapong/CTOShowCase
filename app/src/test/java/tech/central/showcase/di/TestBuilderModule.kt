package tech.central.showcase.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tech.central.showcase.base.BaseTestHolderActivity
import tech.central.showcase.base.BaseTestHolderFragment
import tech.central.showcase.di.scope.PerActivity
import tech.central.showcase.di.scope.PerFragment

@Module
abstract class TestBuilderModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [BaseTestHolderModule::class])
    abstract fun bindBaseTestHolderActivity(): BaseTestHolderActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [TestFragmentViewModelModule::class])
    abstract fun bindBaseTestHolderFragment(): BaseTestHolderFragment
}