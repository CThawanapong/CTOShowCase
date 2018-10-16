package tech.central.showcase.base.epoxy.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import tech.central.showcase.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT)
class EpoxyLoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        private val TAG = EpoxyLoadingView::class.java.simpleName
    }

    init {
        initInflate()
        initInstance(context, attrs)
    }

    private fun initInflate() {
        View.inflate(context, R.layout.list_item_loading, this)
    }

    private fun initInstance(context: Context, attrs: AttributeSet?) {
        attrs?.let {

        }
    }
}