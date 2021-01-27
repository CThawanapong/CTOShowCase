package tech.central.showcase.post_detail

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
abstract class PostDetailViewModelModule {
    companion object {
        @Provides
        fun provideArgs(fragment: PostDetailFragment): Bundle? = fragment.arguments
    }

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    abstract fun productDetailViewModelFactory(factory: PostDetailViewModel.Factory): ViewModelAssistedFactory<out ViewModel>

    @Binds
    abstract fun savedStateRegistryOwner(fragment: PostDetailFragment): SavedStateRegistryOwner
}