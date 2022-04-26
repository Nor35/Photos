package com.nor35.photos.feature.album.data.repository

import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.data.database.PhotoEntity
import com.nor35.photos.data.remote.PhotoApi
import com.nor35.photos.data.remote.dto.toDataBaseModel
import com.nor35.photos.domain.Constants.NUMBER_OF_PHOTOS_ON_PAGE
import com.nor35.photos.feature.album.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoApi: PhotoApi,
    private val photoDao: PhotoDao
) : PhotoRepository {

    override suspend fun getPhoto(): PhotoEntity {

        val photoEntity = photoApi.getPhoto().toDataBaseModel()
        val id = photoDao.insertPhoto(photoEntity)
        Timber.i("Load photo with id = $id")
        photoEntity.id = id

        return photoEntity
    }

    override suspend fun getAlbum(): List<PhotoEntity> {

        return withContext(Dispatchers.IO) {
            val tasks = List(NUMBER_OF_PHOTOS_ON_PAGE) { async(Dispatchers.IO) { getPhoto() } }
            tasks.awaitAll()
        }
    }

    override suspend fun getRandomPhotoFromDB(): PhotoEntity {
        return photoDao.getRandomPhoto()
    }

    override suspend fun getAlbumFromDB(): List<PhotoEntity> {
        return photoDao.getAlbumFromDB()
    }

    override suspend fun deleteAllPhotosFromDB() {
        photoDao.deleteAll()
    }
}
