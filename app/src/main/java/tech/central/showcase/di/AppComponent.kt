package tech.central.showcase.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import tech.central.showcase.ShowCaseApplication
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BuilderModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        AssistedModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance @Named("MOCK_ENDPOINT") endpoint: String
        ): AppComponent
    }

    fun inject(showCaseApplication: ShowCaseApplication)
}