package tech.central.showcase.post.usecase

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import tech.central.showcase.base.model.Post
import tech.central.showcase.base.provider.MockServiceProvider
import javax.inject.Inject

class LoadPostUseCase @Inject constructor(
        private val mockServiceProvider: MockServiceProvider) {
    fun execute(): Single<List<Post>> {
        return Single.zip(mockServiceProvider.getPosts(), mockServiceProvider.getUsers(), BiFunction { posts, users ->
            posts.map { post ->
                post.copy(user = users.find { it.id == post.userId })
            }
        })
    }
}