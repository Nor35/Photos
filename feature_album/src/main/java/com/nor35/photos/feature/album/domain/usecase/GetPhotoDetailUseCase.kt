package com.nor35.photos.feature.album.domain.usecase

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.data.database.toPhotoDetailDomainModel
import com.nor35.photos.feature.album.domain.model.PhotoDetail
import com.nor35.photos.feature.album.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPhotoDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(photoId: Long): Flow<Resource<PhotoDetail>> = flow {
        try {
            emit(Resource.Loading<PhotoDetail>())
            val photo = repository.getPhoto(photoId).toPhotoDetailDomainModel()
            emit(Resource.Success<PhotoDetail>(photo))
        } catch (e: HttpException) {
            emit(
                Resource.Error<PhotoDetail>(
                    "Error getting photo detail" +
                        ": ${e.localizedMessage ?: "An unexpected error occured"}"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<PhotoDetail>(
                    "Error getting photo detail " +
                        ": ${e.localizedMessage ?: "Couldn't reach server." +
                            "Check your internet connection"}"
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error<PhotoDetail>(
                    "Error getting photo detail " +
                        ": ${e.localizedMessage ?: "Couldn't reach server." +
                            "Unknown error in GetPhotoUseCase class"}"
                )
            )
        }
    }
}