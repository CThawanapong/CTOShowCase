package tech.central.showcase.post.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import tech.central.showcase.base.epoxy.view.epoxyLoadingView
import tech.central.showcase.post.controller.model.PostRelay
import tech.central.showcase.post.controller.model.post
import tech.central.showcase.post.model.PostViewState
import javax.inject.Inject

class PostController @Inject constructor() : TypedEpoxyController<PostViewState>() {

    //Data Members
    private val postRelay by lazy { PublishRelay.create<PostRelay>() }

    override fun buildModels(data: PostViewState?) {
        data?.run {
            posts.forEach { post ->
                post {
                    id(post.id)
                    post(post)
                    postRelay(postRelay)
                }
            }
        } ?: epoxyLoadingView {
            id("loading")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    fun bindPostRelay(): Observable<PostRelay> = postRelay
}