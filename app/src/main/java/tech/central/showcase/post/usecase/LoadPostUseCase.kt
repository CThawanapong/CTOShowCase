package tech.central.showcase.post.usecase

import io.reactivex.Single
import io.reactivex.functions.BiFunction

import tech.central.showcase.base.provider.MockServiceProvider
import javax.inject.Inject
import tech.central.showcase.base.model.Post
import tech.central.showcase.base.model.User

class LoadPostUseCase @Inject constructor(
        private val mockServiceProvider: MockServiceProvider
) {

    fun execute(): Single<List<Post>> {
        //get all posts
        val singlePostList = mockServiceProvider.getPosts()

        //get all users
        val singeUserList = mockServiceProvider.getUsers()

        //combine users and posts
        return singlePostList.zipWith(singeUserList, BiFunction { posts, users -> bindUserToPost(posts, users) });
    }

    private fun bindUserToPost(posts: List<Post>, users: List<User>): List<Post> {
        posts.forEach { post -> post.user = findUserInfo(post.userId, users) }
        return posts;
    }

    private fun findUserInfo(id: Int, users: List<User>): User? {
        return users.find { user -> user.id == id };
    }


}