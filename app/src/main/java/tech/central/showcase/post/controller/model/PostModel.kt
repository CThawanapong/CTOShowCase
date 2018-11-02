package tech.central.showcase.post.controller.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.Relay
import io.reactivex.rxkotlin.subscribeBy
import tech.central.showcase.R
import tech.central.showcase.base.epoxy.EpoxyBaseViewHolder
import kotlinx.android.synthetic.main.list_item_post.view.*
import tech.central.showcase.base.model.Post

@EpoxyModelClass(layout = R.layout.list_item_post)
abstract class PostModel : EpoxyModelWithHolder<EpoxyBaseViewHolder>() {
    @EpoxyAttribute
    lateinit var post: Post

    @EpoxyAttribute
    lateinit var detailRelay: Relay<Post>

    override fun bind(holder: EpoxyBaseViewHolder) {
        holder.itemView.apply {
            with(post) {
                textTitle.text = title
                textUserName.text = user?.name
                textUserEmail.text = user?.email
            }

            layoutContent.clicks()
                    .map { post }
                    .subscribeBy(
                            onNext = detailRelay::accept
                    )
        }
    }
}