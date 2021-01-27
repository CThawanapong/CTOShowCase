package tech.central.showcase.base.service

import io.reactivex.Single
import retrofit2.http.GET
import tech.central.showcase.base.model.Photo
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User

interface MockService {
    /**
     * Get list of photos from mock api
     */
    @GET("photos")
    fun photos(
    ): Single<List<Photo>>

    @GET("posts")
    fun posts(): Single<List<Post>>

    @GET("users")
    fun users(): Single<List<User>>
}