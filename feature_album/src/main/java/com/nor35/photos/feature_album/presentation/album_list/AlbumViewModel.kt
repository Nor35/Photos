package com.nor35.photos.feature_album.presentation.album_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nor35.photos.domain.Constants.NUMBER_OF_PHOTOS_ON_PAGE
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.domain.use_case.GetPhotoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase
): ViewModel() {

    private val _liveData = MutableLiveData(PhotoState())
    val liveData = _liveData

    init {
        getPhoto()
    }

    private fun getPhoto() {
        getPhotoUseCase.invoke().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val data = result.data
                    if(data == null )
                        _liveData.value = PhotoState(error = "Photos not reseived")
                    else
                        _liveData.value = PhotoState(album = data  )
                }
                is Resource.Error -> {
                    _liveData.value = PhotoState(error = result.message?:"An unexpected error occured")
                }
                is Resource.Loading -> {
                    _liveData.value = PhotoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}