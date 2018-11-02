package tech.central.showcase.base.service

import io.reactivex.Single
import retrofit2.http.GET
import tech.central.showcase.base.model.Photo
import tech.central.showcase.base.model.Post
import tech.central.showcase.base.model.User

interface MockService {
    /**
     * Get list of photos from mock api
     */
    @GET("photos")
    fun photos(
    ): Single<List<Photo>>

    /**
     * TODO
     * Specified the endpoint to retrieve post content from API
     *
     * The content can be retrieve from the following url:
     * https://jsonplaceholder.typicode.com/posts
     */

    /**
     * Get list of posts from mock api
     */
    @GET("posts")
    fun getPosts(
    ): Single<List<Post>>

    /**
     * Get list of users from mock api
     */
    @GET("users")
    fun getUsers(
    ): Single<List<User>>

}