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
    }
}