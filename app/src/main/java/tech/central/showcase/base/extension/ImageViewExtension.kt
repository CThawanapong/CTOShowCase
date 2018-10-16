package tech.central.showcase.base.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import tech.central.showcase.base.GlideApp

fun ImageView.loadUrl(context: Context, imageUrl: String) {
    GlideApp.with(context)
            .load(imageUrl)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .centerInside()
            .into(this)
}

fun ImageView.loadUrlCropCenter(context: Context, imageUrl: String) {
    GlideApp.with(context)
            .load(imageUrl)
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .centerCrop()
            .into(this)
}