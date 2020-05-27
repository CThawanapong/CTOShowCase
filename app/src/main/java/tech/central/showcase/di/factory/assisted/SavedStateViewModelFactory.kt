package tech.central.showcase.di.factory.assisted

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import tech.central.showcase.di.factory.ViewModelAssistedFactory

import javax.inject.Inject
import javax.inject.Provider

class SavedStateViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModelAssistedFactory<out ViewModel>>>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        val creator: Provider<out ViewModelAssistedFactory<out ViewModel>> =
            (creators[modelClass]) ?: creators.filterKeys { modelClass.isAssignableFrom(it) }
                .values
                .firstOrNull()
            ?: throw IllegalArgumentException("unknown model class $modelClass")

        try {
            return creator.get().create(handle) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}