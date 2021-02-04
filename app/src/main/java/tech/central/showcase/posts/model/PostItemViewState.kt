package tech.central.showcase.posts.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import tech.central.showcase.base.model.post.Post
import tech.central.showcase.base.model.user.User

@Parcelize
data class PostItemViewState (
        val posts: List<Post> = emptyList(),
        val users: List<User> = emptyList()
):Parcelable