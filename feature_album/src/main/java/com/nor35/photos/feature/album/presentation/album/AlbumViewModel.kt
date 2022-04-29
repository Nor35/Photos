package com.nor35.photos.feature.album.presentation.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.usecase.GetAlbumUseCase
import com.nor35.photos.feature.album.domain.usecase.GetPhotoUseCase
import com.nor35.photos.feature.album.domain.usecase.GetSavedAlbumUseCase
import com.nor35.photos.feature.album.domain.usecase.ReloadAllPhotosUseCase
import com.nor35.photos.feature.album.domain.usecase.UseCaseInterface
import com.nor35.photos.feature.album.presentation.album.state.PhotoState
import com.nor35.photos.presentation.routing.RouterSources
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AlbumViewModel @AssistedInject constructor(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getReloadAllPhotosUseCase: ReloadAllPhotosUseCase,
    private val getSavedAlbumUseCaseFactory: GetSavedAlbumUseCase.GetSavedAlbumUseCaseFactory,
    @Assisted private val photoIdArray: LongArray?
) : ViewModel() {

    private val _liveData = MutableLiveData(PhotoState())
    val liveData = _liveData

    private var savedAlbumUseCase: GetSavedAlbumUseCase? = null

    init {

        if (photoIdArray != null && photoIdArray.size >= 0) {
            savedAlbumUseCase = getSavedAlbumUseCaseFactory.create(photoIdArray)
            invokeUseCase(savedAlbumUseCase!!)
        } else {
            invokeUseCase(getAlbumUseCase)
        }
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
                is Resource.Loading -> {
                    _liveData.value = PhotoState(isLoading = true)
                }
                is Resource.Success -> {
                    val data = result.data
                    if (data == null)
                        _liveData.value = PhotoState(error = "Photos not received")
                    else
                        _liveData.value = PhotoState(album = data)
                }
                else -> {
                    _liveData.value = PhotoState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun navigateToPhotoDetail(photoId: Long) {

        RouterSources.albumFragmentRouterSource?.moveToPhotoDetail(photoId)
    }

    @AssistedFactory
    interface AlbumViewModelFactory {
        fun create(photoIdArray: LongArray?): AlbumViewModel
    }
}
