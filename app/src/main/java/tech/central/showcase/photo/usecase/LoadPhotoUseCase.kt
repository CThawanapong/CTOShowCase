package tech.central.showcase.photo.usecase

import io.reactivex.Single
import tech.central.showcase.base.model.Photo
import tech.central.showcase.base.provider.MockServiceProvider
import javax.inject.Inject

class LoadPhotoUseCase @Inject constructor(
        private val mockServiceProvider: MockServiceProvider
) {
    fun execute(): Single<List<Photo>> {
        return mockServiceProvider.photos()
            .map { photos ->
                photos.mapIndexed { index, photo ->
                    photo.copy(
                        thumbnailUrl = "https://picsum.photos/id/$index/200/200.jpg",
                        url = "https://picsum.photos/id/$index/600/600.jpg"
                    )
                }
            }
    }
}