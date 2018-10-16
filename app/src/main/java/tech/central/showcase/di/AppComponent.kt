package tech.central.showcase.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import tech.central.showcase.ShowCaseApplication
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, BuilderModule::class, NetworkModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun mockEndpoint(@Named("MOCK_ENDPOINT") endpoint: String): Builder

        fun build(): AppComponent
    }

    fun inject(showCaseApplication: ShowCaseApplication)
}