package tech.central.showcase.post.usecase

import io.reactivex.Single
import tech.central.showcase.base.model.Post
import tech.central.showcase.base.model.User
import tech.central.showcase.base.provider.MockServiceProvider
import javax.inject.Inject

class LoadPostUseCase @Inject constructor(
        private val mockServiceProvider: MockServiceProvider
) {
    fun execute(users: List<User>): Single<List<Post>> {
        return mockServiceProvider.posts().map { posts ->
            posts.map { post ->
                post.copy(
                        userId = post.userId,
                        id = post.id,
                        title = post.title,
                        body = post.body,
                        name = users.joinToString("") { user ->
                            when (user.userId) {
                                post.userId -> user.name
                                else -> ""
                            }
                        },
                        email = users.joinToString("") {user ->
                            when (user.userId) {
                                post.userId -> user.email
                                else -> ""
                            }
                        }
                )
            }
        }
    }
}

