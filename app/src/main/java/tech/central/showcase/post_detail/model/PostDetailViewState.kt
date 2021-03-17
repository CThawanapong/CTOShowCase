package tech.central.showcase.post_detail.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import tech.central.showcase.base.model.Post

@Parcelize
data class PostDetailViewState(
        val post: Post
) : Parcelable
