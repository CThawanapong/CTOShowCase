package tech.central.showcase.photo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Photo
import tech.central.showcase.photo.usecase.LoadPhotoUseCase

class PhotoViewModel(
        application: Application,
        private val loadPhotoUseCase: LoadPhotoUseCase,
        private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PhotoViewModel::class.java.simpleName
    }

    protected val disposables by lazy { CompositeDisposable() }

    private val photoLiveData by lazy { MutableLiveData<List<Photo>>() }

    fun bindPhotoLiveData(): LiveData<List<Photo>> {
        if (photoLiveData.value == null) {
            loadPhotos()
        }

        return photoLiveData
    }

    private fun loadPhotos() {
        disposables += loadPhotoUseCase.execute()
                .subscribeOn(schedulersFacade.io)
                .observeOn(schedulersFacade.ui)
                .subscribeBy(
                        onError = {
                            photoLiveData.value = emptyList()
                        },
                        onSuccess = {
                            photoLiveData.value = it
                        }
                )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}