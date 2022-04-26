package com.nor35.photos.domain.repository

import com.nor35.photos.data.database.PhotoEntity

interface PhotoRepository {


    suspend fun getPhoto(): PhotoEntity

    suspend fun getPhoto(photoId: Long): PhotoEntity

    suspend fun getAlbum(): List<PhotoEntity>

    suspend fun getRandomPhotoFromDB(): PhotoEntity?

    suspend fun getAlbumFromDB(): List<PhotoEntity>?

    suspend fun deleteAllPhotosFromDB()
}