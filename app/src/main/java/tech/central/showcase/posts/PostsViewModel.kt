package tech.central.showcase.posts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.R
import tech.central.showcase.base.Event
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User
import tech.central.showcase.posts.model.PostItemViewState
import tech.central.showcase.posts.usecase.LoadPostItemUseCase
import java.util.*
import javax.inject.Inject

class PostsViewModel @Inject constructor(
        application: Application,
        private val loadPostItemUseCase: LoadPostItemUseCase,
        private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PostsViewModel::class.java.simpleName
    }


    // View State
    val postItemStateLiveData: MediatorLiveData<PostItemViewState> by lazy {
        MediatorLiveData<PostItemViewState>().apply {
            addSource(postListLiveData, fun(source: List<Post>) {
                postItemStateLiveData.value = postItemStateLiveData.value?.copy(
                        posts = source
                ) ?: PostItemViewState(posts = source)
            })
            addSource(userListLiveData, fun(source: List<User>) {
                postItemStateLiveData.value = postItemStateLiveData.value?.copy(
                        users = source
                ) ?: PostItemViewState(users = source)
            })
        }
    }

    private val _snackBarMessage = MutableLiveData<Event<String>>()
    val snackBarMessage: LiveData<Event<String>>
        get() = _snackBarMessage

    // Live Data
    val postListLiveData by lazy { MutableLiveData<List<Post>>() }

    // Live Data
    val userListLiveData by lazy { MutableLiveData<List<User>>() }

    // Data Members
    private val disposables by lazy { CompositeDisposable() }


    private var needShowSnackBar = false

    fun loadInit() {
        loadPostItem()
    }

    private fun mergeEmittedItems(): BiFunction<List<Post>, List<User>, Pair<List<Post>, List<User>>?> {
        return BiFunction<List<Post>, List<User>, Pair<List<Post>, List<User>>?> { posts, users -> Pair(posts, users) }
    }

    fun loadPostItem() {
        needShowSnackBar = postListLiveData.value == null || postListLiveData.value!!.isEmpty()
        disposables += Single.zip(loadPostItemUseCase.executeLoadPosts(), loadPostItemUseCase.executeLoadUsers(), mergeEmittedItems()).subscribeOn(schedulersFacade.io)
                .observeOn(schedulersFacade.ui)
                .subscribe(
                        { result ->
                            postListLiveData.value = result?.first
                            userListLiveData.value = result?.second
                            if (needShowSnackBar) _snackBarMessage.value =
                                    Event(getApplication<Application>().resources.getString(R.string.posts_loaded))

                        },
                        { _ ->
                            postListLiveData.value = emptyList()
                            userListLiveData.value = emptyList()
                            if (needShowSnackBar) _snackBarMessage.value =
                                    Event(getApplication<Application>().resources.getString(R.string.posts_load_error))
                        }
                )
    }


    fun sortItemAscending() {
        if (userListLiveData.value != null) {
            userListLiveData.value = userListLiveData.value?.sortedWith(compareBy { it.name.toLowerCase() })
        }
    }

    fun sortItemDescending() {
        if (userListLiveData.value != null) {
            userListLiveData.value = userListLiveData.value?.sortedWith(compareByDescending { it.name.toLowerCase() })
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}