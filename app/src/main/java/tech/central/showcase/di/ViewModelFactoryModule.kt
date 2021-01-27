package tech.central.showcase.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import tech.central.showcase.di.factory.ViewModelFactory
import tech.central.showcase.di.qualifier.ViewModelKey
import tech.central.showcase.photo.PhotoViewModel
import tech.central.showcase.posts.PostsViewModel

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PhotoViewModel::class)
    internal abstract fun photoViewModel(viewModel: PhotoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    internal abstract fun postViewModel(viewModel: PostsViewModel): ViewModel
}