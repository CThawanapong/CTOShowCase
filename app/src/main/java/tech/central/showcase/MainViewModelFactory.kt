package tech.central.showcase

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.photo.PhotoViewModel
import tech.central.showcase.photo.usecase.LoadPhotoUseCase

class MainViewModelFactory(
        private val application: Application,
        private val loadPhotoUseCase: LoadPhotoUseCase,
        private val schedulersFacade: SchedulersFacade
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PhotoViewModel::class.java) -> PhotoViewModel(application, loadPhotoUseCase, schedulersFacade) as T
            //TODO viewModel class should be provided from this factory

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}