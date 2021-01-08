package tech.central.showcase.post.controller.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.jakewharton.rxrelay2.Relay
import kotlinx.android.synthetic.main.list_item_post.view.*
import tech.central.showcase.R
import tech.central.showcase.base.epoxy.EpoxyBaseViewHolder
import tech.central.showcase.base.model.Post

@EpoxyModelClass(layout = R.layout.list_item_post)
abstract class PostModel : EpoxyModelWithHolder<EpoxyBaseViewHolder>() {

    @EpoxyAttribute
    lateinit var post: Post

    @EpoxyAttribute
    lateinit var postRelay: Relay<PostRelay>

    override fun bind(holder: EpoxyBaseViewHolder) {
        holder.itemView.apply {
            tvPostTitle.apply {
                text = post.title
                transitionName = context.getString(R.string.shared_element_title, post.id)
            }
            tvUserName.apply {
                text = post.user?.name
                transitionName = context.getString(R.string.shared_element_name, post.id)
            }
            tvUserEmail.apply {
                text = post.user?.email
                transitionName = context.getString(R.string.shared_element_email, post.id)
            }

            rootView.clicks().subscribeBy(
                    onError = { it.printStackTrace() },
                    onNext = { postRelay.accept(PostRelay(post, tvPostTitle, tvUserName, tvUserEmail)) }
            )
        }
    }
}