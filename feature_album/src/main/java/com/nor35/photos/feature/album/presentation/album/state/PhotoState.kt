package com.nor35.photos.feature.album.presentation.album.state

import com.nor35.photos.feature.album.domain.model.Photo

data class PhotoState(
    val isLoading: Boolean = false,
    val album: List<Photo> = emptyList(),
    val error: String = ""
)
