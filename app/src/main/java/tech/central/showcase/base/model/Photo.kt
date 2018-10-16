package tech.central.showcase.base.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
        @field:[Expose SerializedName("albumId")]
        val albumId: Int = 0,

        @field:[Expose SerializedName("id")]
        val id: Int = 0,

        @field:[Expose SerializedName("title")]
        val title: String = "",

        @field:[Expose SerializedName("url")]
        val url: String = "",

        @field:[Expose SerializedName("thumbnailUrl")]
        val thumbnailUrl: String = ""
) : Parcelable