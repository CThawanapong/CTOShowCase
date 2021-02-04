package tech.central.showcase

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tech.central.showcase.dashboard.DashboardFragment
import tech.central.showcase.di.scope.PerFragment
import tech.central.showcase.photo.PhotoFragment
import tech.central.showcase.photo.PhotoModule
import tech.central.showcase.photo_detail.PhotoDetailFragment
import tech.central.showcase.photo_detail.PhotoDetailModule
import tech.central.showcase.photo_detail.PhotoDetailViewModelModule
import tech.central.showcase.post_detail.PostDetailFragment
import tech.central.showcase.post_detail.PostDetailModule
import tech.central.showcase.post_detail.PostDetailViewModelModule
import tech.central.showcase.posts.PostItemModule
import tech.central.showcase.posts.PostsFragment

@Module
abstract class MainFragmentProvider {
    @PerFragment
    @ContributesAndroidInjector(modules = [])
    abstract fun bindDashboardFragment(): DashboardFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PhotoModule::class])
    abstract fun bindPhotoFragment(): PhotoFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PostItemModule::class])
    abstract fun bindPostFragment(): PostsFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PhotoDetailModule::class, PhotoDetailViewModelModule::class])
    abstract fun bindPhotoDetailFragment(): PhotoDetailFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PostDetailModule::class, PostDetailViewModelModule::class])
    abstract fun bindPostDetailFragment(): PostDetailFragment
}