package tech.central.showcase.post.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import tech.central.showcase.base.model.Post

@Parcelize
data class PostViewState(
        val posts: List<Post> = emptyList()
) : Parcelable
