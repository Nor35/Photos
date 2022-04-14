package com.nor35.photos.feature.album.domain.model

data class PhotoDetail(
    val id: Long,
    val imageUrl: String,
    val width: Int = 0,
    val height: Int = 0,
    val filter: String = ""
)
