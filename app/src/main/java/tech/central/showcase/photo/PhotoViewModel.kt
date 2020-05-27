package tech.central.showcase.photo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Photo
import tech.central.showcase.photo.model.PhotoViewState
import tech.central.showcase.photo.usecase.LoadPhotoUseCase
import javax.inject.Inject

class PhotoViewModel @Inject constructor(
    application: Application,
    private val loadPhotoUseCase: LoadPhotoUseCase,
    private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PhotoViewModel::class.java.simpleName
    }

    // View State
    val photoViewStateLiveData: MediatorLiveData<PhotoViewState> by lazy {
        MediatorLiveData<PhotoViewState>().apply {
            addSource(photoListLiveData) { source ->
                photoViewStateLiveData.value = photoViewStateLiveData.value?.copy(
                    photos = source
                ) ?: PhotoViewState(photos = source)
            }
        }
    }

    // Live Data
    val photoListLiveData by lazy { MutableLiveData<List<Photo>>() }

    // Data Members
    private val disposables by lazy { CompositeDisposable() }

    fun loadInit() {
        loadPhotos()
    }

    fun loadPhotos() {
        disposables += loadPhotoUseCase.execute()
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {
                    photoListLiveData.value = emptyList()
                },
                onSuccess = {
                    photoListLiveData.value = it
                }
            )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}