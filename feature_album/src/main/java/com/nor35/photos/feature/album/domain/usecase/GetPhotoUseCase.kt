package com.nor35.photos.feature.album.domain.usecase

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.model.Photo
import com.nor35.photos.feature.album.domain.model.toDomainModel
import com.nor35.photos.feature.album.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) : UseCaseInterface {

    override operator fun invoke(): Flow<Resource<List<Photo>>> = flow {

        try {
            emit(Resource.Loading<List<Photo>>())
            val photo = repository.getPhoto().toDomainModel()
            emit(Resource.Success<List<Photo>>(listOf(photo)))
        } catch (e: HttpException) {
            emit(getDbPhotoIfExist(e, "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(
                getDbPhotoIfExist(
                    e,
                    "Couldn't reach server. Check your internet connection"
                )
            )
        } catch (e: Exception) {
            emit(getDbPhotoIfExist(e, "Unknown error in GetPhotoUseCase class"))
        }
    }

    private suspend fun getDbPhotoIfExist(e: Exception, errorMessage: String):
        Resource<List<Photo>> {

        val photoFromDB = repository.getRandomPhotoFromDB()
        val message = "Error getting album: ${e.localizedMessage ?: errorMessage}"
        Timber.e(message)

        return if (photoFromDB == null)
            Resource.Error<List<Photo>>(message)
        else
            Resource.Success<List<Photo>>(listOf(photoFromDB.toDomainModel()))
    }
}
