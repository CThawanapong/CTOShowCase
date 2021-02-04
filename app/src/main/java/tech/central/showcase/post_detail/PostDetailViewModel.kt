package tech.central.showcase.post_detail

import android.app.Application
import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.extension.toBundle
import tech.central.showcase.base.model.Photo
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User
import tech.central.showcase.di.factory.ViewModelAssistedFactory
import tech.central.showcase.photo_detail.PhotoDetailFragmentArgs
import tech.central.showcase.photo_detail.PhotoDetailViewModel
import tech.central.showcase.photo_detail.model.PhotoDetailViewState
import tech.central.showcase.post_detail.model.PostDetailViewState

class PostDetailViewModel @AssistedInject constructor(
        application: Application,
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PostDetailViewModel::class.java.simpleName
    }

    @AssistedInject.Factory
    interface Factory :
            ViewModelAssistedFactory<PostDetailViewModel>

    private val postDetailFragmentArgs by lazy {
        PostDetailFragmentArgs.fromBundle(savedStateHandle.toBundle)
    }

    // View State
    val postDetailViewState: MediatorLiveData<PostDetailViewState> by lazy {
        MediatorLiveData<PostDetailViewState>().apply {
            addSource(postItemLiveData) { source ->
                postDetailViewState.value = postDetailViewState.value?.copy(
                        postItem = source
                ) ?: PostDetailViewState(postItem = source)
            }
        }
    }

    // Live Data
    private val postItemLiveData by lazy { MutableLiveData<Pair<Post,User>>() }

    // Data Members
    private val disposables by lazy { CompositeDisposable() }

    fun loadInit() {
        loadPostItem()
    }

    private fun loadPostItem() {
        postItemLiveData.value = Pair(postDetailFragmentArgs.post,postDetailFragmentArgs.user)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}