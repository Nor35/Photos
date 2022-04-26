package com.nor35.photos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun getPhotoDao(): PhotoDao
}
