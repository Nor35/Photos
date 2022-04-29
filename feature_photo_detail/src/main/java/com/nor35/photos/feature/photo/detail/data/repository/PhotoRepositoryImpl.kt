package com.nor35.photos.feature.photo.detail.data.repository

import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.data.database.PhotoEntity
import com.nor35.photos.feature.photo.detail.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoDao: PhotoDao
) : PhotoRepository {

    override suspend fun getPhoto(photoId: Long): PhotoEntity? {
        return photoDao.getPhoto(photoId)
    }
}
