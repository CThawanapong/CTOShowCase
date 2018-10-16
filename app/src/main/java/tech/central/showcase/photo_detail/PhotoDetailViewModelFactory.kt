package tech.central.showcase.photo_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Photo

class PhotoDetailViewModelFactory(
        private val application: Application,
        private val schedulersFacade: SchedulersFacade
) : ViewModelProvider.Factory {
    lateinit var photo: Photo

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoDetailViewModel::class.java)) {
            return PhotoDetailViewModel(application, photo, schedulersFacade) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}