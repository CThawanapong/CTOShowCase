package tech.central.showcase.photo_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Photo

class PhotoDetailViewModel(
        application: Application,
        private val photo: Photo,
        private val schedulersFacade: SchedulersFacade
) : AndroidViewModel(application) {
    companion object {
        private val TAG = PhotoDetailViewModel::class.java.simpleName
    }

    protected val disposables by lazy { CompositeDisposable() }

    private val photoLiveData by lazy { MutableLiveData<Photo>() }

    fun bindPhotoLiveData(): LiveData<Photo> {
        if (photoLiveData.value == null) {
            photoLiveData.value = photo
        }
        return photoLiveData
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}