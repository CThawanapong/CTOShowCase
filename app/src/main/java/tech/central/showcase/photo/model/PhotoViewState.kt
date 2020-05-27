package tech.central.showcase.photo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import tech.central.showcase.base.model.Photo

@Parcelize
data class PhotoViewState(
    val photos: List<Photo> = emptyList()
) : Parcelable