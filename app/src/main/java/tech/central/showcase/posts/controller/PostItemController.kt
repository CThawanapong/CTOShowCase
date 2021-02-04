package tech.central.showcase.posts.controller

import android.util.Log
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import tech.central.showcase.base.epoxy.view.epoxyLoadingView
import tech.central.showcase.base.model.Photo
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User
import tech.central.showcase.photo.controller.PhotoController
import tech.central.showcase.posts.controller.model.PostItemModel
import tech.central.showcase.photo.model.PhotoViewState
import tech.central.showcase.posts.controller.model.postItem
import tech.central.showcase.posts.model.PostItemViewState
import javax.inject.Inject

class PostItemController @Inject constructor() : TypedEpoxyController<PostItemViewState>() {
    companion object {
        @JvmStatic
        private val TAG = PostItemController::class.java.simpleName
    }

    //Data Members
    private val detailRelay by lazy { PublishRelay.create<Triple<Post, User, FragmentNavigator.Extras>>() }

    override fun buildModels(data: PostItemViewState?) {
        data?.run {
            users.forEach { users ->
                posts.forEach { post ->
                    if (users.id.equals(post.userId))
                        postItem {
                            id(post.id)
                            postItem(Pair(post, users))
                            detailRelay(detailRelay)
                        }
                }

            }
        } ?: epoxyLoadingView {
            id("loading2")
        }
    }

    fun bindDetailRelay(): Observable<Triple<Post, User, FragmentNavigator.Extras>> = detailRelay
}