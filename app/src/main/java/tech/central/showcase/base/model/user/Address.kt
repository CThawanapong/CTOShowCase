package tech.central.showcase.base.model.user
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
        @field:[Expose SerializedName("city")]
        val city: String ="",
        @field:[Expose SerializedName("geo")]
        val geo: Geo = Geo(),
        @field:[Expose SerializedName("street")]
        val street: String = "",
        @field:[Expose SerializedName("suite")]
        val suite: String = "",
        @field:[Expose SerializedName("zipcode")]
        val zipcode: String = ""
): Parcelable