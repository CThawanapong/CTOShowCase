package tech.central.showcase.post_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import tech.central.showcase.base.model.Post
import tech.central.showcase.post_detail.model.PostDetailViewState

class PostDetailViewModel(app: Application) : AndroidViewModel(app) {
    // View State
    val postDetailViewState: MediatorLiveData<PostDetailViewState> by lazy {
        MediatorLiveData<PostDetailViewState>().apply {
            addSource(postLiveData) { source ->
                postDetailViewState.value = PostDetailViewState(post = source)
            }
        }
    }

    // Live Data
    private val postLiveData by lazy { MutableLiveData<Post>() }

    // Data Members
    private val disposables by lazy { CompositeDisposable() }

    fun loadPost(post: Post) {
        postLiveData.value = post
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}