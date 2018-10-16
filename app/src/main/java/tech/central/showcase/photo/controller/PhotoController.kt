package tech.central.showcase.photo.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import tech.central.showcase.base.epoxy.view.epoxyLoadingView
import tech.central.showcase.base.model.Photo
import tech.central.showcase.photo.controller.model.photo
import javax.inject.Inject

class PhotoController @Inject constructor() : TypedEpoxyController<List<Photo>>() {
    companion object {
        @JvmStatic
        private val TAG = PhotoController::class.java.simpleName
    }

    //Data Members
    private val detailRelay by lazy { PublishRelay.create<Photo>() }

    override fun buildModels(data: List<Photo>?) {
        when {
            data == null -> {
                epoxyLoadingView {
                    id("loading")
                }
            }
            data.isNotEmpty() -> {
                data.forEach {
                    photo {
                        id(it.id)
                        photo(it)
                        detailRelay(detailRelay)
                    }
                }
            }
            else -> {
            }
        }
    }

    fun showLoading() {
        setData(null)
    }

    fun bindDetailRelay(): Observable<Photo> = detailRelay
}