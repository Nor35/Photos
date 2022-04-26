package com.nor35.photos.feature.album.domain.model

import com.nor35.photos.data.database.PhotoEntity

data class PhotoDetail(
    val id: Long,
    val imageUrl: String,
    val width: Int = 0,
    val height: Int = 0,
    val filter: String = ""
)

fun PhotoEntity.toPhotoDetailDomainModel() = PhotoDetail(
    id = id,
    imageUrl = url,
    width = width,
    height = height
)