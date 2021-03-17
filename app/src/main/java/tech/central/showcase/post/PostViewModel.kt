package tech.central.showcase.post

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.R
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Post
import tech.central.showcase.post.model.PostViewState
import tech.central.showcase.post.usecase.LoadPostUseCase
import tech.central.showcase.post.usecase.LoadUserUseCase
import javax.inject.Inject

class PostViewModel @Inject constructor(
        application: Application,
        private val loadPostUseCase: LoadPostUseCase,
        private val loadUserUseCase: LoadUserUseCase,
        private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PostViewModel::class.java.simpleName
    }

    //View State
    val postViewStateLiveData: MediatorLiveData<PostViewState> by lazy {
        MediatorLiveData<PostViewState>().apply {
            addSource(postListLiveData) {source ->
                        postViewStateLiveData.value = postViewStateLiveData.value?.copy(
                        posts = source
                ) ?: PostViewState(posts = source)
            }
        }
    }

    // Live Data
    val postListLiveData by lazy { MutableLiveData<List<Post>>() }
    val sortedLiveData by lazy { MutableLiveData<Boolean>() }
    val isLoadingLiveData by lazy { MutableLiveData<Boolean>() }

    //Data Members
    private val disposable by lazy { CompositeDisposable() }

    fun loadInit() {
        loadPosts()
    }

    fun loadPosts() {
        disposable += loadUserUseCase.execute()
                .flatMap { loadPostUseCase.execute(it) }
                .subscribeOn(schedulersFacade.io)
                .observeOn(schedulersFacade.ui)
//                .doOnSubscribe { isLoadingLiveData.value = true }
//                .doOnEvent{ _, _ -> isLoadingLiveData.value = false }
                .subscribeBy(
                        onError = {
                            postListLiveData.value = emptyList()
                        },
                        onSuccess = {
                            when (sortedLiveData.value) {
                                false -> postListLiveData.value = it.sortedByDescending { post -> post.title }
                                else -> {
                                    postListLiveData.value = it.sortedBy { post -> post.title }
                                    sortedLiveData.value = true
                                }
                            }
                        }
                )
    }

    fun sortedListMenu() {
        when (sortedLiveData.value) {
            false -> {
                postListLiveData.value = postListLiveData.value?.sortedBy { post -> post.title }
                sortedLiveData.value = true
            }
            true -> {
                postListLiveData.value = postListLiveData.value?.sortedByDescending { post -> post.title }
                sortedLiveData.value = false
            }

        }
    }

    fun sortList() {
        postListLiveData.value = emptyList()
        sortedLiveData.value = true
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}