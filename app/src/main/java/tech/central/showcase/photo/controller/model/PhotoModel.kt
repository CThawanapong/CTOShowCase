package tech.central.showcase.photo.controller.model

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.Relay
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.list_item_photo.view.*
import tech.central.showcase.R
import tech.central.showcase.base.epoxy.EpoxyBaseViewHolder
import tech.central.showcase.base.extension.loadUrlCropCenter
import tech.central.showcase.base.model.Photo

@EpoxyModelClass(layout = R.layout.list_item_photo)
abstract class PhotoModel : EpoxyModelWithHolder<EpoxyBaseViewHolder>() {
    @EpoxyAttribute
    lateinit var photo: Photo

    @EpoxyAttribute
    lateinit var detailRelay: Relay<Photo>

    override fun bind(holder: EpoxyBaseViewHolder) {
        holder.itemView.apply {
            with(photo) {
                imageView.loadUrlCropCenter(context, thumbnailUrl)
                textView.text = title
            }

            layoutContent.clicks()
                    .map { photo }
                    .subscribeBy(
                            onNext = detailRelay::accept
                    )
        }
    }
}