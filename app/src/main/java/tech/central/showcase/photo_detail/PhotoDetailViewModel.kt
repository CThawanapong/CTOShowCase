package tech.central.showcase.photo_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.disposables.CompositeDisposable
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.extension.toBundle
import tech.central.showcase.base.model.Photo
import tech.central.showcase.di.factory.ViewModelAssistedFactory
import tech.central.showcase.photo_detail.model.PhotoDetailViewState

class PhotoDetailViewModel @AssistedInject constructor(
    application: Application,
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PhotoDetailViewModel::class.java.simpleName
    }

    @AssistedInject.Factory
    interface Factory :
        ViewModelAssistedFactory<PhotoDetailViewModel>

    private val photoDetailFragmentArgs by lazy {
        PhotoDetailFragmentArgs.fromBundle(savedStateHandle.toBundle)
    }

    // View State
    val photoDetailViewState: MediatorLiveData<PhotoDetailViewState> by lazy {
        MediatorLiveData<PhotoDetailViewState>().apply {
            addSource(photoLiveData) { source ->
                photoDetailViewState.value = photoDetailViewState.value?.copy(
                    photo = source
                ) ?: PhotoDetailViewState(photo = source)
            }
        }
    }

    // Live Data
    private val photoLiveData by lazy { MutableLiveData<Photo>() }

    // Data Members
    private val disposables by lazy { CompositeDisposable() }

    fun loadInit() {
        loadPhoto()
    }

    private fun loadPhoto() {
        photoLiveData.value = photoDetailFragmentArgs.photo
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}