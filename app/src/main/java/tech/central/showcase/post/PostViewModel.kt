package tech.central.showcase.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.base.LiveEvent
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Post
import tech.central.showcase.post.model.PostViewState
import tech.central.showcase.post.model.SortType
import tech.central.showcase.post.usecase.LoadPostUseCase
import javax.inject.Inject

class PostViewModel @Inject constructor(
        application: Application,
        private val loadPostUseCase: LoadPostUseCase,
        private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {

    // View State
    val postViewStateLiveData: MediatorLiveData<PostViewState> by lazy {
        MediatorLiveData<PostViewState>().apply {
            addSource(postListLiveData) { source ->
                postViewStateLiveData.value = PostViewState(posts = source)
            }

            addSource(currentSortType) { sortType ->
                postListLiveData.value?.let { posts ->
                    val sortedList = when (sortType) {
                        SortType.NONE -> posts
                        SortType.ASC -> posts.sortedBy { it.title }
                        SortType.DESC -> posts.sortedByDescending { it.title }
                    }
                    postViewStateLiveData.value = PostViewState(posts = sortedList)
                    onSortedLiveEvent.postValue(true)
                }
            }
        }
    }

    // Live Data
    private val postListLiveData by lazy { MutableLiveData<List<Post>>() }

    private val currentSortType by lazy { MutableLiveData<SortType>(SortType.NONE) }

    val onLoadedLiveEvent by lazy { LiveEvent<Boolean>() }
    val onSortedLiveEvent by lazy { LiveEvent<Boolean>() }

    // Data Members
    private val disposables by lazy { CompositeDisposable() }

    fun loadPosts() {
        val shouldShowSnackBar = postListLiveData.value == null //Only show snackBar for the first time when posts is null
        disposables += loadPostUseCase.execute()
                .subscribeOn(schedulersFacade.io)
                .observeOn(schedulersFacade.ui)
                .subscribeBy(
                        onError = {
                            postListLiveData.value = emptyList()
                        },
                        onSuccess = {
                            postListLiveData.value = it
                            if (shouldShowSnackBar) onLoadedLiveEvent.value = true
                        }
                )
    }

    fun sortPostList(sortType: SortType) {
        currentSortType.value = sortType
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}