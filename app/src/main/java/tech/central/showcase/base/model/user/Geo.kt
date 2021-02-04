package tech.central.showcase.base.model.user

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geo(
        @field:[Expose SerializedName("lat")]
        val lat: String = "",
        @field:[Expose SerializedName("lng")]
        val lng: String = ""
): Parcelable