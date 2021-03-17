package tech.central.showcase.post.controller.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.Relay
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.list_item_post.view.*
import tech.central.showcase.R
import tech.central.showcase.base.epoxy.EpoxyBaseViewHolder
import tech.central.showcase.base.model.Post
import tech.central.showcase.post.relay.PostRelay

@EpoxyModelClass(layout = R.layout.list_item_post)
abstract class PostModel : EpoxyModelWithHolder<EpoxyBaseViewHolder>() {
    @EpoxyAttribute
    lateinit var post: Post

    @EpoxyAttribute
    lateinit var detailRelay: Relay<PostRelay>

    override fun bind(holder: EpoxyBaseViewHolder) {
        holder.itemView.apply {
            content_layout.transitionName = post.id.toString()

            with(post) {
                titleTextViewListItem.text = title
                nameTextViewListItem.text = name
                emailTextViewListItem.text = email

                root_view.clicks().map {
                    PostRelay(
                            post =  post,
                            layout = content_layout
                    )
                }.subscribeBy(
                        onError = {},
                        onNext = detailRelay::accept
                )
            }
        }

    }
}