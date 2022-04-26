package com.nor35.photos.feature.photo.detail.domain.repository

import com.nor35.photos.data.database.PhotoEntity

interface PhotoRepository {

    suspend fun getPhoto(photoId: Long): PhotoEntity
}
