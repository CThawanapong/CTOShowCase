package tech.central.showcase.post.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import tech.central.showcase.base.epoxy.view.epoxyLoadingView
import tech.central.showcase.post.controller.model.post
import tech.central.showcase.post.model.PostViewState
import tech.central.showcase.post.relay.PostRelay
import javax.inject.Inject

class PostController @Inject constructor() : TypedEpoxyController<PostViewState>() {
    companion object {
        @JvmStatic
        private val TAG = PostController::class.java.simpleName
    }

    //Data Members
    private val detailRelay by lazy { PublishRelay.create<PostRelay>() }

    override fun buildModels(data: PostViewState?) {
        data?.run {
            posts.forEach { post ->
                post {
                    id(post.id)
                    post(post)
                    detailRelay(detailRelay)
                }
            }
        } ?: epoxyLoadingView {
            id("loading")
        }
    }

    fun bindDetailRelay() = detailRelay

}