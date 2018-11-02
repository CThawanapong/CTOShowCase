package tech.central.showcase.post.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import tech.central.showcase.base.epoxy.view.epoxyLoadingView
import javax.inject.Inject
import tech.central.showcase.base.model.Post
import tech.central.showcase.post.controller.model.post


class PostController @Inject constructor() : TypedEpoxyController<List<Post>>() {
    companion object {
        @JvmStatic
        private val TAG = PostController::class.java.simpleName
    }

    //Data Members
    private val detailRelay by lazy { PublishRelay.create<Post>() }

    override fun buildModels(data: List<Post>?) {
        when {
            data == null -> {
                epoxyLoadingView {
                    id("loading")
                }
            }
            data.isNotEmpty() -> {
                data.forEach {
                    post {
                        id(it.id)
                        post(it)
                        detailRelay(detailRelay)
                    }
                }
            }
            else -> {
            }
        }
    }

    fun showLoading() {
        setData(null)
    }

    fun bindDetailRelay(): Observable<Post> = detailRelay
}