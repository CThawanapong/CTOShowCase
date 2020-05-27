package tech.central.showcase.photo

import android.os.Build
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import tech.central.showcase.base.BaseTest
import tech.central.showcase.base.BaseTestHolderActivity
import java.net.HttpURLConnection
import kotlin.properties.Delegates
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O])
class PhotoViewModelTest : BaseTest() {
    override val isMockServerEnabled: Boolean
        get() = true

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Data Member
    private lateinit var activity: ActivityScenario<BaseTestHolderActivity>
    private lateinit var viewModel: PhotoViewModel
    private var shouldMockFailResponse by Delegates.notNull<Boolean>()

    override fun configureMockServer() {
        super.configureMockServer()
        if (isMockServerEnabled) {
            mockServer.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    return when (val path = request.path) {
                        null -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                        else -> {
                            when {
                                path.contains("photos") -> {
                                    when {
                                        !shouldMockFailResponse -> mockResponseFromFile(
                                            fileName = "photo_whenSuccess.json",
                                            responseCode = HttpURLConnection.HTTP_OK
                                        )
                                        else -> mockResponseFromFile(
                                            fileName = "photo_whenFailed.json",
                                            responseCode = HttpURLConnection.HTTP_OK
                                        )
                                    }
                                }
                                else -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                            }
                        }
                    }
                }
            }
        }
    }

    @Before
    override fun setUp() {
        super.setUp()
        this.activity = ActivityScenario.launch(BaseTestHolderActivity::class.java)
        this.activity.onActivity { baseTestHolderActivity ->
            this.viewModel = ViewModelProvider(
                baseTestHolderActivity,
                baseTestHolderActivity.mViewModelFactory
            )
                .get(PhotoViewModel::class.java)
        }
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun loadPhoto_whenSuccess() {
        shouldMockFailResponse = false

        // Observe
        this.viewModel.photoListLiveData.observeForever { }

        // Check Initial
        assertEquals(
            null,
            this.viewModel.photoListLiveData.value,
            "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPhotos()

        // Check result
        assertEquals(
            true,
            this.viewModel.photoListLiveData.value?.isNotEmpty(),
            "Photo should not be empty"
        )
    }

    @Test
    fun loadPhoto_whenFailed() {
        shouldMockFailResponse = true

        // Observe
        this.viewModel.photoListLiveData.observeForever { }

        // Check Initial
        assertEquals(
            null,
            this.viewModel.photoListLiveData.value,
            "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPhotos()

        // Check result
        assertEquals(
            true,
            this.viewModel.photoListLiveData.value?.isEmpty(),
            "Photo should be empty"
        )
    }
}