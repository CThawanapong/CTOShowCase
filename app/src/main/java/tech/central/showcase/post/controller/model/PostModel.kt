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

@EpoxyModelClass(layout = R.layout.list_item_post)
abstract class PostModel : EpoxyModelWithHolder<EpoxyBaseViewHolder>() {

    @EpoxyAttribute
    var index: Int = 0

    @EpoxyAttribute
    lateinit var post: Post

    @EpoxyAttribute
    lateinit var detailRelay: Relay<Post>

    override fun bind(holder: EpoxyBaseViewHolder) {
        holder.itemView.apply {
            with(post) {
                textViewPostTitle.text = String.format(context.getString(R.string.format_post_title), index, title)
                textViewUserName.text = user?.name ?: ""
                textViewUserEmail.text = user?.email ?: ""
            }

            cardView.clicks().map { post }.subscribeBy(
                    onError = { it.printStackTrace() },
                    onNext = detailRelay::accept
            )
        }
    }
}