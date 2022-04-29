package com.nor35.photos.feature.album.domain.usecase

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.model.Photo
import com.nor35.photos.feature.album.domain.model.toDomainModel
import com.nor35.photos.feature.album.domain.repository.PhotoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class GetSavedAlbumUseCase @AssistedInject constructor(
    private val repository: PhotoRepository,
    @Assisted private val photoIdArray: LongArray
) : UseCaseInterface {

    override operator fun invoke(): Flow<Resource<List<Photo>>> = flow {

        emit(Resource.Loading<List<Photo>>())

        photoIdArray.forEach { photoId ->
            try {
                var photo = repository.getPhoto(photoId)

                if (photo != null) {
                    emit(Resource.Success<List<Photo>>(listOf(photo.toDomainModel())))
                } else {
                    photo = repository.getRandomPhotoFromDB()
                    if (photo != null) {
                        emit(Resource.Success<List<Photo>>(listOf(photo.toDomainModel())))
                    } else {
                        emit(
                            Resource.Error<List<Photo>>(
                                "repository.getRandomPhotoFromDB() return null." +
                                    " Maybe the database is empty"
                            )
                        )
                    }
                }
            } catch (e: HttpException) {
                emit(getDbPhotoIfExist(e, "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(
                    getDbPhotoIfExist(
                        e, "Couldn't reach server. Check your internet connection"
                    )
                )
            } catch (e: Exception) {
                emit(getDbPhotoIfExist(e, "Unknown error in GetSavedAlbumUseCase class"))
            }
        }
    }

    private suspend fun getDbPhotoIfExist(e: Exception, errorMessage: String):
        Resource<List<Photo>> {

        val photoFromDB = repository.getRandomPhotoFromDB()
        val message = "Error getting photo: ${e.localizedMessage ?: errorMessage}"
        Timber.e(message)

        return if (photoFromDB == null)
            Resource.Error<List<Photo>>(message)
        else
            Resource.Success<List<Photo>>(listOf(photoFromDB.toDomainModel()))
    }

    @AssistedFactory
    interface GetSavedAlbumUseCaseFactory {
        fun create(photoIdArray: LongArray?): GetSavedAlbumUseCase
    }
}
