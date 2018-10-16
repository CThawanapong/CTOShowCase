package tech.central.showcase

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import hu.akarnokd.rxjava2.debug.RxJavaAssemblyTracking
import tech.central.showcase.di.DaggerAppComponent
import javax.inject.Inject

class ShowCaseApplication : Application(), HasActivityInjector {
    companion object {
        @JvmStatic
        private val TAG = ShowCaseApplication::class.java.simpleName
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        RxJavaAssemblyTracking.enable()

        DaggerAppComponent
                .builder()
                .application(this)
                .mockEndpoint(BuildConfig.MOCK_ENDPOINT)
                .build()
                .inject(this)

        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}