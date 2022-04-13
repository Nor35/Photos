package com.nor35.photos.feature_album.domain.model

data class Photo(
    val id: Long = 0L,
    val imageUrl: String,
    val width: Int = 0,
    val height: Int = 0
)
