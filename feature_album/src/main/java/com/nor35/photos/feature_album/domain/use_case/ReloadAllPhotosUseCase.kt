package com.nor35.photos.feature_album.domain.use_case

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.data.database.toDomainModel
import com.nor35.photos.feature_album.domain.model.Photo
import com.nor35.photos.feature_album.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ReloadAllPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) : UseCaseInterface {

    override operator fun invoke(): Flow<Resource<List<Photo>>> = flow {
        try {
            emit(Resource.Loading<List<Photo>>())
            repository.deleteAllPhotosFromDB()
            val album = repository.getAlbum().map { it.toDomainModel() }
            emit(Resource.Success<List<Photo>>(album))
        } catch (e: HttpException) {
            emit(getDbAlbumIfExist(e, "An unexpected error occured"))
        } catch (e: IOException) {
            emit(getDbAlbumIfExist(e, "Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(getDbAlbumIfExist(e, "Unknown error in GetPhotoUseCase class"))
        }
    }

    private suspend fun getDbAlbumIfExist(e: Exception, errorMessage: String): Resource<List<Photo>> {

        val albumFromDB = repository.getAlbumFromDB()
        val message = "Error getting album: ${e.localizedMessage ?: errorMessage}"
        Timber.e(message)

        return if (albumFromDB == null || albumFromDB.isEmpty())
            Resource.Error<List<Photo>>(message)
        else
            Resource.Success<List<Photo>>(albumFromDB.map { it.toDomainModel() })
    }
}
