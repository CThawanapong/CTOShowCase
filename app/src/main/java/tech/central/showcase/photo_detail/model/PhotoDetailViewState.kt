package tech.central.showcase.photo_detail.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import tech.central.showcase.base.model.Photo

@Parcelize
data class PhotoDetailViewState(
    val photo: Photo
) : Parcelable