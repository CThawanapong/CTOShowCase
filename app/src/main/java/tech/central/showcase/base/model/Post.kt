package tech.central.showcase.base.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
        @field:[Expose SerializedName("body")]
        val body: String = "",

        @field:[Expose SerializedName("id")]
        val id: Int = 0,

        @field:[Expose SerializedName("title")]
        val title: String = "",

        @field:[Expose SerializedName("userId")]
        val userId: Int = 0,

        val user: User? = null,

        val index: Int = 0
) : Parcelable