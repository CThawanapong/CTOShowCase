package tech.central.showcase

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import hu.akarnokd.rxjava2.debug.RxJavaAssemblyTracking
import tech.central.showcase.di.TestAppComponent
import javax.inject.Inject

class TestShowCaseApplication : Application(), HasAndroidInjector {
    companion object {
        @JvmStatic
        private val TAG = TestShowCaseApplication::class.java.simpleName
    }

    lateinit var appComponent: TestAppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        RxJavaAssemblyTracking.enable()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}