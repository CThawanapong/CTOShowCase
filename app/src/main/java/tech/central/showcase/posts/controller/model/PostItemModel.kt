package tech.central.showcase.posts.controller.model

import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.Relay
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.list_item_post.view.*
import tech.central.showcase.R
import tech.central.showcase.base.epoxy.EpoxyBaseViewHolder
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User

@EpoxyModelClass(layout = R.layout.list_item_post)
abstract class PostItemModel : EpoxyModelWithHolder<EpoxyBaseViewHolder>() {
    @EpoxyAttribute
    lateinit var postItem: Pair<Post,User>


    @EpoxyAttribute
    lateinit var detailRelay: Relay<Triple<Post,User,FragmentNavigator.Extras>>

    override fun bind(holder: EpoxyBaseViewHolder) {
        holder.itemView.apply {
            with(postItem) {
                txt_title_post.text = postItem.first.title
                txt_name.text = postItem.second.name
                txt_email.text = postItem.second.email
            }
            val extra = FragmentNavigatorExtras(cardViewItem to "${postItem.first.id}")
            cardViewItem.transitionName = "${postItem.first.id}"
            val mapItem = Triple(postItem.first,postItem.second,extra)
            layoutContentPost.clicks().map { mapItem }.subscribeBy(onNext = { detailRelay.accept(it) })
        }
    }
}