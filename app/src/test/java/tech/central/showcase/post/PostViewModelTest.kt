package tech.central.showcase.post

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
import tech.central.showcase.photo.PhotoViewModel
import tech.central.showcase.posts.PostsViewModel
import java.net.HttpURLConnection
import kotlin.properties.Delegates
import kotlin.test.assertEquals
//

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O])
class PostViewModelTest : BaseTest() {
    override val isMockServerEnabled: Boolean
        get() = true

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Data Member
    private lateinit var activity: ActivityScenario<BaseTestHolderActivity>
    private lateinit var viewModel: PostsViewModel
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
                                path.contains("posts") -> {
                                    when {
                                        !shouldMockFailResponse -> mockResponseFromFile(
                                                fileName = "post_whenSuccess.json",
                                                responseCode = HttpURLConnection.HTTP_OK
                                        )
                                        else -> mockResponseFromFile(
                                                fileName = "post_whenFailed.json",
                                                responseCode = HttpURLConnection.HTTP_OK
                                        )
                                    }
                                }
                                path.contains("users") -> {
                                    when {
                                        !shouldMockFailResponse -> mockResponseFromFile(
                                                fileName = "user_whenSuccess.json",
                                                responseCode = HttpURLConnection.HTTP_OK
                                        )
                                        else -> mockResponseFromFile(
                                                fileName = "user_whenFailed.json",
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
                    .get(PostsViewModel::class.java)
        }
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun loadPosts_whenSuccess() {
        shouldMockFailResponse = false

        // Observe
        this.viewModel.postListLiveData.observeForever { }

        // Check Initial
        assertEquals(
                null,
                this.viewModel.postListLiveData.value,
                "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPostItem()

        // Check result
        assertEquals(
                true,
                this.viewModel.postListLiveData.value?.isNotEmpty(),
                "Posts should not be empty"
        )
    }

    @Test
    fun loadPosts_whenFailed() {
        shouldMockFailResponse = true

        // Observe
        this.viewModel.postListLiveData.observeForever { }

        // Check Initial
        assertEquals(
                null,
                this.viewModel.postListLiveData.value,
                "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPostItem()

        // Check result
        assertEquals(
                true,
                this.viewModel.postListLiveData.value?.isEmpty(),
                "Posts should be empty"
        )
    }

    @Test
    fun loadUsers_whenSuccess() {
        shouldMockFailResponse = false

        // Observe
        this.viewModel.userListLiveData.observeForever { }

        // Check Initial
        assertEquals(
                null,
                this.viewModel.userListLiveData.value,
                "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPostItem()

        // Check result
        assertEquals(
                true,
                this.viewModel.userListLiveData.value?.isNotEmpty(),
                "Users should not be empty"
        )
    }

    @Test
    fun loadUsers_whenFailed() {
        shouldMockFailResponse = true

        // Observe
        this.viewModel.userListLiveData.observeForever { }

        // Check Initial
        assertEquals(
                null,
                this.viewModel.userListLiveData.value,
                "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPostItem()

        // Check result
        assertEquals(
                true,
                this.viewModel.userListLiveData.value?.isEmpty(),
                "Users should be empty"
        )
    }

    @Test
    fun loadPost_sortAsc() {
        shouldMockFailResponse = false

        // Observe
        this.viewModel.userListLiveData.observeForever { }

        // Check Initial
        assertEquals(
                null,
                this.viewModel.userListLiveData.value,
                "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPostItem()

        // Check result
        assertEquals(
                true,
                this.viewModel.userListLiveData.value?.isNotEmpty(),
                "Posts should not be empty"
        )

        this.viewModel.sortItemAscending()
        val expectedSortedList = viewModel.userListLiveData.value?.sortedBy { it.name.toLowerCase() }
        assertEquals(expectedSortedList, this.viewModel.userListLiveData.value,
                "Posts should be sorted in Asc order")
    }

    @Test
    fun loadPost_sortDesc() {
        shouldMockFailResponse = false

        // Observe
        this.viewModel.userListLiveData.observeForever { }

        // Check Initial
        assertEquals(
                null,
                this.viewModel.userListLiveData.value,
                "Before perform test initial state should be null."
        )

        // Perform
        this.viewModel.loadPostItem()

        // Check result
        assertEquals(
                true,
                this.viewModel.userListLiveData.value?.isNotEmpty(),
                "Posts should not be empty"
        )

        this.viewModel.sortItemDescending()
        val expectedSortedList = viewModel.userListLiveData.value?.sortedByDescending { it.name.toLowerCase() }
        assertEquals(expectedSortedList, this.viewModel.userListLiveData.value,
                "Posts should be sorted in Desc order")
    }

}