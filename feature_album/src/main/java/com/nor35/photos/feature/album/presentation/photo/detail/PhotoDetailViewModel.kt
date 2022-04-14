package com.nor35.photos.feature.album.presentation.photo.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.usecase.GetPhotoDetailUseCase
import com.nor35.photos.feature.album.presentation.photo.detail.state.PhotoDetailState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData(PhotoDetailState())
    val liveData = _liveData

    fun getPhoto(photoId: Long) {
        getPhotoDetailUseCase.invoke(photoId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val data = result.data
                    if (data == null)
                        _liveData.value = PhotoDetailState(error = "Photos not reseived")
                    else
                        _liveData.value = PhotoDetailState(photoDetail = data)
                }
                is Resource.Error -> {
                    _liveData.value = PhotoDetailState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _liveData.value = PhotoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
