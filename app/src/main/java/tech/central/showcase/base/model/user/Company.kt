package tech.central.showcase.base.model.user

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
        @field:[Expose SerializedName("bs")]
        val bs: String = "",
        @field:[Expose SerializedName("catchPhrase")]
        val catchPhrase: String = "",
        @field:[Expose SerializedName("name")]
        val name: String = ""
) : Parcelable