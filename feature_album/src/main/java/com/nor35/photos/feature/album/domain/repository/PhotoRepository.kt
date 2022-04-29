package com.nor35.photos.feature.album.domain.repository

import com.nor35.photos.data.database.PhotoEntity

interface PhotoRepository {

    suspend fun getPhoto(): PhotoEntity

    suspend fun getAlbum(): List<PhotoEntity>

    suspend fun getRandomPhotoFromDB(): PhotoEntity?

    suspend fun getAlbumFromDB(): List<PhotoEntity>?

    suspend fun deleteAllPhotosFromDB()

    suspend fun getPhoto(photoId: Long): PhotoEntity?
}
