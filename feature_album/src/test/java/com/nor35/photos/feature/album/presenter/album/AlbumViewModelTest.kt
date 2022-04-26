package com.nor35.photos.feature.album.presenter.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.model.Photo
import com.nor35.photos.feature.album.domain.usecase.GetAlbumUseCase
import com.nor35.photos.feature.album.domain.usecase.GetPhotoUseCase
import com.nor35.photos.feature.album.domain.usecase.ReloadAllPhotosUseCase
import com.nor35.photos.feature.album.presentation.album.AlbumViewModel
import com.nor35.photos.feature.album.presentation.album.state.PhotoState
import com.nor35.photos.feature.album.presenter.PresenterFixtures
import com.nor35.photos.feature.album.presenter.PresenterFixtures.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumViewModelTest {

    @MockK
    internal lateinit var mockGetAlbumUseCase: GetAlbumUseCase
    @MockK
    internal lateinit var mockGetPhotoUseCase: GetPhotoUseCase
    @MockK
    internal lateinit var mockReloadAllPhotosUseCase: ReloadAllPhotosUseCase

    private lateinit var viewModel: AlbumViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun init_AlbumViewModel_fetches_PhotoStatel() {

        // given
        coEvery {
            mockGetAlbumUseCase.invoke()
        } returns(PresenterFixtures.getPhotoFlow())

        // when
        viewModel = AlbumViewModel(
            mockGetAlbumUseCase, mockGetPhotoUseCase,
            mockReloadAllPhotosUseCase
        )

        // then
        assertEquals(
            viewModel.liveData.getOrAwaitValue(),
            PhotoState(album = PresenterFixtures.getListPhoto())
        )
    }

    @Test
    fun getPhoto_fetches_PhotoState() {

        // given
        coEvery {
            mockGetAlbumUseCase.invoke()
        } returns flow { emit(Resource.Loading<List<Photo>>()) }
        coEvery {
            mockGetPhotoUseCase.invoke()
        } returns PresenterFixtures.getPhotoFlow()

        viewModel = AlbumViewModel(
            mockGetAlbumUseCase, mockGetPhotoUseCase,
            mockReloadAllPhotosUseCase
        )

        // when
        viewModel.getPhoto()

        // then
        assertEquals(
            viewModel.liveData.getOrAwaitValue(),
            PhotoState(album = PresenterFixtures.getListPhoto())
        )
    }
}
