package com.nor35.photos.feature.album.presentation.photo.detail.state

import com.nor35.photos.feature.album.domain.model.PhotoDetail

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photoDetail: PhotoDetail? = null,
    val error: String = ""
)
