package com.nor35.photos.feature_album.domain.repository

import com.nor35.photos.feature_album.data.database.PhotoEntity
import com.nor35.photos.feature_album.data.remote.dto.LoremFlickrDto
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getPhoto(): PhotoEntity

    suspend fun getPhoto(photoId: Long): PhotoEntity

    suspend fun getAlbum(): List<PhotoEntity>

    suspend fun getRandomPhotoFromDB(): PhotoEntity?

    suspend fun getAlbumFromDB(): List<PhotoEntity>?

    suspend fun deleteAllPhotosFromDB()
}