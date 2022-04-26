package com.nor35.photos.feature.album.presentation.photo.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.usecase.GetPhotoDetailUseCase
import com.nor35.photos.feature.album.presentation.photo.detail.state.PhotoDetailState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PhotoDetailViewModel @AssistedInject  constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    @Assisted val photoId: Long
) : ViewModel() {

    private val _mutableState = mutableStateOf(PhotoDetailState())
    val mutableState = _mutableState

    init {
        getPhoto(photoId)
    }

    private fun getPhoto(photoId: Long) {
        getPhotoDetailUseCase.invoke(photoId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val data = result.data
                    if (data == null)
                        _mutableState.value = PhotoDetailState(error = "Photos not reseived")
                    else
                        _mutableState.value = PhotoDetailState(photoDetail = data)
                }
                is Resource.Error -> {
                    _mutableState.value = PhotoDetailState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _mutableState.value = PhotoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

@AssistedFactory
interface PhotoDetailViewModelFactory {
    fun create(photoId: Long): PhotoDetailViewModel
}
