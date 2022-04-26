package com.nor35.photos.feature.album.presentation.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.usecase.GetAlbumUseCase
import com.nor35.photos.feature.album.domain.usecase.GetPhotoUseCase
import com.nor35.photos.feature.album.domain.usecase.ReloadAllPhotosUseCase
import com.nor35.photos.feature.album.domain.usecase.UseCaseInterface
import com.nor35.photos.feature.album.presentation.album.state.PhotoState
import com.nor35.photos.presentation.routing.RouterSources
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getReloadAllPhotosUseCase: ReloadAllPhotosUseCase
) : ViewModel() {

    private val _liveData = MutableLiveData(PhotoState())
    val liveData = _liveData

    init {
        getAlbum()
    }

    private fun getAlbum() {
        invokeUseCase(getAlbumUseCase)
    }

    fun getPhoto() {
        invokeUseCase(getPhotoUseCase)
    }

    fun reloadAllPhotos() {
        invokeUseCase(getReloadAllPhotosUseCase)
    }

    private fun invokeUseCase(useCase: UseCaseInterface) {
        useCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val data = result.data
                    if (data == null)
                        _liveData.value = PhotoState(error = "Photos not reseived")
                    else
                        _liveData.value = PhotoState(album = data)
                }
                is Resource.Error -> {
                    _liveData.value = PhotoState(error = result.message ?: "An unexpected error occured")
                }
                is Resource.Loading -> {
                    _liveData.value = PhotoState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun navigateToPhotoDetail(photoId: Long) {

        RouterSources.albumFragmentRouterSource?.moveToPhotoDetail(photoId)
    }
}
