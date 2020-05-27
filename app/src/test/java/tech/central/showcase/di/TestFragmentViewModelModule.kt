package tech.central.showcase.di

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import tech.central.showcase.base.BaseTestHolderFragment
import tech.central.showcase.di.factory.ViewModelAssistedFactory
import tech.central.showcase.di.qualifier.ViewModelKey
import tech.central.showcase.photo_detail.PhotoDetailViewModel

@Module
abstract class TestFragmentViewModelModule {
    @Module
    companion object {
        @Nullable
        @Provides
        @JvmStatic
        fun provideArgs(fragment: BaseTestHolderFragment): Bundle? = fragment.arguments
    }

    @Binds
    @IntoMap
    @ViewModelKey(PhotoDetailViewModel::class)
    abstract fun productDetailViewModelFactory(factory: PhotoDetailViewModel.Factory): ViewModelAssistedFactory<out ViewModel>

    @Binds
    abstract fun savedStateRegistryOwner(fragment: BaseTestHolderFragment): SavedStateRegistryOwner
}