package tech.central.showcase.base.model.user
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        @field:[Expose SerializedName("address")]
        val address: Address = Address(),
        @field:[Expose SerializedName("company")]
        val company: Company = Company(),
        @field:[Expose SerializedName("email")]
        val email: String = "",
        @field:[Expose SerializedName("id")]
        val id: Int = 0,
        @field:[Expose SerializedName("name")]
        val name: String = "",
        @field:[Expose SerializedName("phone")]
        val phone: String = "",
        @field:[Expose SerializedName("username")]
        val username: String = "",
        @field:[Expose SerializedName("website")]
        val website: String = ""

):Parcelable