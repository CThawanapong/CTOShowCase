package tech.central.showcase.posts.usecase

import io.reactivex.Single
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User
import tech.central.showcase.base.provider.MockServiceProvider
import javax.inject.Inject

class LoadPostItemUseCase @Inject constructor(
        private val mockServiceProvider: MockServiceProvider
) {
    fun executeLoadPosts(): Single<List<Post>> {
        return mockServiceProvider.posts()
    }
    fun executeLoadUsers(): Single<List<User>> {
        return mockServiceProvider.user()
    }

}
