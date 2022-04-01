package com.nor35.photos.feature_album.presentation.album_list

import com.nor35.photos.feature_album.domain.model.Photo

data class PhotoState(
    val isLoading: Boolean = false,
    val album: List<Photo> = emptyList(),
    val error: String = ""
)