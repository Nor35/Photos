package com.nor35.photos.feature_album.presentation.photo_detail.state

import com.nor35.photos.feature_album.domain.model.PhotoDetail

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photoDetail: PhotoDetail? = null,
    val error: String = ""
)
