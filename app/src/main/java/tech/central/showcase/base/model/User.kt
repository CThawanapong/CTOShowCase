package tech.central.showcase.base.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        @field: [Expose SerializedName("id")]
        val userId: Int = 0,

        @field: [Expose SerializedName("name")]
        val name: String = "",

        @field: [Expose SerializedName("email")]
        val email: String = ""
) : Parcelable {
        fun toPost() : Post = Post(
                userId = this.userId,
                name = this.name,
                email = this.email
        )
}
