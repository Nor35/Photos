package com.nor35.photos.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PhotoEntity.TABLE_NAME)
data class PhotoEntity(

    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    companion object {
        const val TABLE_NAME = "photo_entity"
    }
}
