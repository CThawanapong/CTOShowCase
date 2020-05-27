package tech.central.showcase.base

import tech.central.showcase.di.factory.assisted.SavedStateViewModelFactory
import javax.inject.Inject

class BaseTestHolderFragment : BaseFragment() {
    companion object {
        @JvmStatic
        private val TAG = BaseTestHolderFragment::class.java.simpleName
    }

    // Injection
    @Inject
    lateinit var mSavedStateViewModelFactory: SavedStateViewModelFactory
}