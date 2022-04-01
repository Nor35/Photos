package com.nor35.photos.feature_album.data.database

import androidx.room.*
import com.nor35.photos.domain.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photoEntity: PhotoEntity): Long

    @Query("SELECT * FROM ${PhotoEntity.TABLE_NAME}")
    fun getAll(): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM ${PhotoEntity.TABLE_NAME} ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomPhoto(): PhotoEntity

    @Query("SELECT * FROM ${PhotoEntity.TABLE_NAME} ORDER BY RANDOM() " +
            "LIMIT ${Constants.NUMBER_OF_PHOTOS_ON_PAGE}")
    suspend fun getAlbumFromDB(): List<PhotoEntity>

    @Query("DELETE FROM ${PhotoEntity.TABLE_NAME}")
    suspend fun deleteAll()

}