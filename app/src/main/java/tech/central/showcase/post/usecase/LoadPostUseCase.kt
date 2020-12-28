package tech.central.showcase.post.usecase

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import tech.central.showcase.base.SchedulersFacade
import tech.central.showcase.base.model.Post
import tech.central.showcase.base.provider.MockServiceProvider
import javax.inject.Inject

class LoadPostUseCase @Inject constructor(
        private val mockServiceProvider: MockServiceProvider,
        private val schedulersFacade: SchedulersFacade
) {
    fun execute(): Single<List<Post>> {
        return Single.zip(mockServiceProvider.posts().subscribeOn(schedulersFacade.io),
                mockServiceProvider.users().subscribeOn(schedulersFacade.io), BiFunction { postList, userList ->
            postList.mapIndexed { index, post ->
                post.copy(index = index, user = userList.find { it.id == post.userId })
            }
        })
    }
}