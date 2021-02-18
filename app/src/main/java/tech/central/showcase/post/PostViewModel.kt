package tech.central.showcase.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Post
import tech.central.showcase.post.model.PostViewState
import tech.central.showcase.post.usecase.LoadPostUseCase
import javax.inject.Inject

class PostViewModel @Inject constructor(
        application: Application,
        private val loadPostUseCase: LoadPostUseCase,
        private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PostViewModel::class.java.simpleName
    }

    // View State
    val postViewStateLiveData: MediatorLiveData<PostViewState> by lazy {
        MediatorLiveData<PostViewState>().apply {
            addSource(postListLiveData) { source ->
                postViewStateLiveData.value = postViewStateLiveData.value?.copy(
                        posts = source
                ) ?: PostViewState(posts = source)
            }

            addSource(sortedPostListLiveData) { source ->
                postViewStateLiveData.value = postViewStateLiveData.value?.copy(
                        posts = source
                ) ?: PostViewState(posts = source)
            }
        }
    }

    // Live Data
    private val postListLiveData by lazy { MutableLiveData<List<Post>>() }

    private val sortedPostListLiveData by lazy { MutableLiveData<List<Post>>() }

    val onPostListLoadedEvent by lazy { MutableLiveData<Boolean>() }

    // Data Members
    private val disposables by lazy { CompositeDisposable() }

    init {
        loadPosts()
    }

    fun loadPosts() {
        disposables += loadPostUseCase.execute()
                //.subscribeOn(schedulersFacade.io)
                .observeOn(schedulersFacade.ui)
                .subscribeBy(
                        onError = {
                            postListLiveData.value = emptyList()
                        },
                        onSuccess = {
                            postListLiveData.value = it
                            onPostListLoadedEvent.value = true
                        }
                )
    }

    /**
     * Sort list post with title in asc order.
     * @param isDesc: is sorted in desc order or not.
     */
    fun sortListPost(isDesc: Boolean = false) {
        postListLiveData.value?.let { listPost ->
            if (isDesc) {
                sortedPostListLiveData.postValue(listPost.sortedByDescending { it.title })
            } else {
                sortedPostListLiveData.postValue(listPost.sortedBy { it.title })
            }
        }
    }

    fun clearIsPostLoadedEvent() {
        onPostListLoadedEvent.value = null
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}