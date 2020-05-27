package tech.central.showcase.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import tech.central.showcase.TestShowCaseApplication
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        TestBuilderModule::class,
        ViewModelFactoryModule::class,
        TestNetworkModule::class,
        AssistedModule::class
    ]
)
interface TestAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun mockEndpoint(@Named("MOCK_ENDPOINT") endpoint: String): Builder

        fun build(): TestAppComponent
    }

    fun inject(showCaseApplication: TestShowCaseApplication)
}