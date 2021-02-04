package tech.central.showcase.base.provider

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import tech.central.showcase.base.model.Photo
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User
import tech.central.showcase.base.service.MockService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockServiceProvider @Inject constructor(
        private val mockService: MockService
) {
    companion object {
        @JvmStatic
        private val TAG = MockServiceProvider::class.java.simpleName
    }

    fun photos(): Single<List<Photo>> {
        return mockService.photos()
    }

    fun posts():Single<List<Post>>{
        return mockService.posts()
    }

    fun user():Single<List<User>>{
        return mockService.users()
    }



}