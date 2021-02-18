package tech.central.showcase.post.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import tech.central.showcase.base.epoxy.view.epoxyLoadingView
import tech.central.showcase.base.model.Post
import tech.central.showcase.post.controller.model.post
import tech.central.showcase.post.model.PostViewState
import javax.inject.Inject

class PostController @Inject constructor() : TypedEpoxyController<PostViewState>() {
    companion object {
        @JvmStatic
        private val TAG = PostController::class.java.simpleName
    }

    //Data Members
    private val detailRelay by lazy { PublishRelay.create<Post>() }

    override fun buildModels(data: PostViewState?) {
        data?.run {
            posts.forEach { post ->
                post {
                    id(post.id)
                    index(post.index)
                    post(post)
                    detailRelay(detailRelay)
                }
            }
        } ?: epoxyLoadingView {
            id("loading")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    fun bindDetailRelay(): Observable<Post> = detailRelay
}