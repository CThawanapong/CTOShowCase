package tech.central.showcase.post

import android.os.Build
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.lifecycle.ViewModelProvider
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import tech.central.showcase.base.BaseTest
import tech.central.showcase.base.BaseTestHolderFragment
import java.net.HttpURLConnection
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O])
class PostViewModelTest : BaseTest() {
    override val isMockServerEnabled: Boolean
        get() = true

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Data Member
    private lateinit var fragment: FragmentScenario<BaseTestHolderFragment>
    private lateinit var viewModel: PostViewModel
    private var shouldMockFailResponse = false

    override fun configureMockServer() {
        super.configureMockServer()
        mockServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (val path = request.path) {
                    null -> MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                    else -> {
                        when {
                            path.contains("users") -> {
                                when {
                                    !shouldMockFailResponse -> mockResponseFromFile(
                                            fileName = "user_whenSuccess.json",
                                            responseCode = HttpURLConnection.HTTP_OK
                                    )
                                    else -> mockResponseFromFile(
                                            fileName = "photo_whenFailed.json",
                                            responseCode = HttpURLConnection.HTTP_OK
                                    )
                                }
                            }
                            path.contains("posts") -> {
                                when {
                                    !shouldMockFailResponse -> mockResponseFromFile(
                                            fileName = "post_whenSuccess.json",
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

    @BeforeTest
    override fun setUp() {
        super.setUp()
        this.fragment = FragmentScenario.launch(BaseTestHolderFragment::class.java)
        this.fragment.onFragment {
            this.viewModel = ViewModelProvider(
                    it,
                    it.mViewModelFactory
            )
                    .get(PostViewModel::class.java)
        }
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun loadPost_whenSuccess() {
        shouldMockFailResponse = false

        this.viewModel.postListLiveData.observeForever {  }

        assertEquals(null, this.viewModel.postListLiveData.value)

        this.viewModel.loadPosts()

        assertEquals(true, this.viewModel.postListLiveData.value?.isNotEmpty())
    }

    @Test
    fun  loadPost_whenFailed() {
        shouldMockFailResponse = true

        this.viewModel.postListLiveData.observeForever {  }

        assertEquals(null, this.viewModel.postListLiveData.value)

        this.viewModel.loadPosts()

        assertEquals(true, this.viewModel.postListLiveData.value?.isEmpty())
    }
}

