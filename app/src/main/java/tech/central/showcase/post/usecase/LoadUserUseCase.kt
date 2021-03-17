package tech.central.showcase.post.usecase

import io.reactivex.Single
import tech.central.showcase.base.model.User
import tech.central.showcase.base.provider.MockServiceProvider
import javax.inject.Inject

class LoadUserUseCase @Inject constructor(
        private val mockServiceProvider: MockServiceProvider
) {
    fun execute() : Single<List<User>> {
        return mockServiceProvider.users().map { users ->
            users.map { user ->
                user.copy(
                        userId = user.userId,
                        name = user.name,
                        email = user.email
                )
            }
        }
    }
}