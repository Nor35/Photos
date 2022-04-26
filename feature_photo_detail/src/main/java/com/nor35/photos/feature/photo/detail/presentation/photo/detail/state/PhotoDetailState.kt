package com.nor35.photos.feature.photo.detail.presentation.photo.detail.state

import com.nor35.photos.feature.photo.detail.domain.model.PhotoDetail

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photoDetail: PhotoDetail? = null,
    val error: String = ""
)
