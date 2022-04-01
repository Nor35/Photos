package com.nor35.photos.feature_album.domain.model

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.data.database.PhotoEntity

data class Photo(
    val imageUrl: String,
    val width: Int = 0,
    val height: Int = 0
)