package tech.central.showcase.base

import androidx.test.core.app.ApplicationProvider
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.gms.ShadowGooglePlayServicesUtil
import tech.central.showcase.TestShowCaseApplication
import tech.central.showcase.di.DaggerTestAppComponent
import tech.central.showcase.di.TestAppComponent

@Config(shadows = [ShadowGooglePlayServicesUtil::class])
abstract class BaseTest {
    lateinit var application: TestShowCaseApplication
    lateinit var mockServer: MockWebServer
    lateinit var testAppComponent: TestAppComponent

    @Before
    open fun setUp() {
        this.application = ApplicationProvider.getApplicationContext()
        val shadowApplication = Shadows.shadowOf(this.application)
        shadowApplication.declareActionUnbindable("com.google.android.gms.analytics.service.START")

        this.configureMockServer()
        this.configureDI()
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
    }

    // MOCK SERVER
    abstract val isMockServerEnabled: Boolean

    open fun configureMockServer() {
        if (isMockServerEnabled) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled) {
            mockServer.shutdown()
        }
    }

    open fun configureDI() {
        this.testAppComponent = DaggerTestAppComponent.builder()
            .application(this.application)
            .mockEndpoint(if (isMockServerEnabled) mockServer.url("/").toString() else "")
            .build()
        this.testAppComponent.inject(this.application)
        this.application.appComponent = this.testAppComponent
    }

    fun mockResponseFromFile(fileName: String, responseCode: Int) = MockResponse()
        .setResponseCode(responseCode)
        .setBody(loadJson(fileName))

    private fun loadJson(path: String): String {
        val inputStream = this.javaClass.classLoader?.getResourceAsStream(path)
        return inputStream?.bufferedReader().use { it?.readText() } ?: ""
    }
}