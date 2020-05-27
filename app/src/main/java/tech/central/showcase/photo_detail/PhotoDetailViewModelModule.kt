package tech.central.showcase.photo_detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import tech.central.showcase.di.factory.ViewModelAssistedFactory
import tech.central.showcase.di.qualifier.ViewModelKey

@Module
abstract class PhotoDetailViewModelModule {
    companion object {
        @Provides
        fun provideArgs(fragment: PhotoDetailFragment): Bundle? = fragment.arguments
    }

    @Binds
    @IntoMap
    @ViewModelKey(PhotoDetailViewModel::class)
    abstract fun productDetailViewModelFactory(factory: PhotoDetailViewModel.Factory): ViewModelAssistedFactory<out ViewModel>

    @Binds
    abstract fun savedStateRegistryOwner(fragment: PhotoDetailFragment): SavedStateRegistryOwner
}