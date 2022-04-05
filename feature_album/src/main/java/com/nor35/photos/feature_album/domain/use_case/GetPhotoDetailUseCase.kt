package com.nor35.photos.feature_album.domain.use_case

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.data.database.toPhotoDetailDomainModel
import com.nor35.photos.feature_album.domain.model.PhotoDetail
import com.nor35.photos.feature_album.domain.repository.PhotoRepository
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
            throw e
//            emit(Resource.Error<PhotoDetail>("Error getting photo detail" +
//                    ": ${e.localizedMessage ?: "An unexpected error occured"}"))
        } catch (e: IOException) {
            throw e
//            emit(Resource.Error<PhotoDetail>("Error getting photo detail " +
//                    ": ${e.localizedMessage ?: "Couldn't reach server." +
//                    "Check your internet connection"}"))
        } catch (e: Exception) {
            throw e
//            emit(Resource.Error<PhotoDetail>("Error getting photo detail " +
//                    ": ${e.localizedMessage ?: "Couldn't reach server." +
//                    "Unknown error in GetPhotoUseCase class"}"))
        }
    }
}