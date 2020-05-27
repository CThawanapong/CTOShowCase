package tech.central.showcase.di.factory.assisted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.central.showcase.di.factory.ViewModelChildAssistedFactory
import javax.inject.Inject
import javax.inject.Provider

class ViewModelChildFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModelChildAssistedFactory<out ViewModel>>>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator: Provider<out ViewModelChildAssistedFactory<out ViewModel>> =
            (creators[modelClass]) ?: creators.filterKeys { modelClass.isAssignableFrom(it) }
                .values
                .firstOrNull()
            ?: throw IllegalArgumentException("unknown model class $modelClass")

        try {
            return creator.get().create() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}