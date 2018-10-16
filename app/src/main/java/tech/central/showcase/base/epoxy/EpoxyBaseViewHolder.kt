package tech.central.showcase.base.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyHolder

class EpoxyBaseViewHolder : EpoxyHolder() {
    companion object {
        @JvmStatic
        private val TAG = EpoxyBaseViewHolder::class.java.simpleName
    }

    lateinit var itemView: View

    override fun bindView(itemView: View) {
        this.itemView = itemView
    }
}