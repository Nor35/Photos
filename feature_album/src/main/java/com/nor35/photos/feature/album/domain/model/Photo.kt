package com.nor35.photos.feature.album.domain.model

import com.nor35.photos.data.database.PhotoEntity

data class Photo(
    val id: Long = 0L,
    val imageUrl: String,
    val width: Int = 0,
    val height: Int = 0
)

fun PhotoEntity.toDomainModel() = Photo(
    id = id,
    imageUrl = url,
    width = width,
    height = height
)
