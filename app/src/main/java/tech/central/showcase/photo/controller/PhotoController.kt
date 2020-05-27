package tech.central.showcase.photo.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import tech.central.showcase.base.epoxy.view.epoxyLoadingView
import tech.central.showcase.base.model.Photo
import tech.central.showcase.photo.controller.model.photo
import tech.central.showcase.photo.model.PhotoViewState
import javax.inject.Inject

class PhotoController @Inject constructor() : TypedEpoxyController<PhotoViewState>() {
    companion object {
        @JvmStatic
        private val TAG = PhotoController::class.java.simpleName
    }

    //Data Members
    private val detailRelay by lazy { PublishRelay.create<Photo>() }

    override fun buildModels(data: PhotoViewState?) {
        data?.run {
            photos.forEach { photo ->
                photo {
                    id(photo.id)
                    photo(photo)
                    detailRelay(detailRelay)
                }
            }
        } ?: epoxyLoadingView {
            id("loading")
            spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount }
        }
    }

    fun bindDetailRelay(): Observable<Photo> = detailRelay
}