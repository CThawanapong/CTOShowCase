package tech.central.showcase.post_detail.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import tech.central.showcase.base.model.Photo
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User

@Parcelize
data class PostDetailViewState(
        val postItem: Pair<Post,User>
) : Parcelable