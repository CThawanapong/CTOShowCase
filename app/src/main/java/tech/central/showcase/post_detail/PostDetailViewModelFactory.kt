package tech.central.showcase.post_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Post

class PostDetailViewModelFactory(
        private val application: Application,
        private val schedulersFacade: SchedulersFacade
) : ViewModelProvider.Factory {
    lateinit var post: Post

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostDetailViewModel::class.java)) {
            return PostDetailViewModel(application, post, schedulersFacade) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}