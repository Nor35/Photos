package com.nor35.photos.feature_album.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nor35.photos.feature_album.domain.model.Photo
import com.nor35.photos.feature_album.domain.model.PhotoDetail

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

fun PhotoEntity.toDomainModel() = Photo(
    id = id,
    imageUrl = url,
    width = width,
    height = height
)

fun PhotoEntity.toPhotoDetailDomainModel() = PhotoDetail(
    id = id,
    imageUrl = url,
    width = width,
    height = height
)
