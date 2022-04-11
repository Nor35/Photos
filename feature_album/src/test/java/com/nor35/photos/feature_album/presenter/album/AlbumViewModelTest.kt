package com.nor35.photos.feature_album.presenter.album

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.domain.model.Photo
import com.nor35.photos.feature_album.domain.use_case.GetAlbumUseCase
import com.nor35.photos.feature_album.domain.use_case.GetPhotoUseCase
import com.nor35.photos.feature_album.domain.use_case.ReloadAllPhotosUseCase
import com.nor35.photos.feature_album.presentation.album.AlbumViewModel
import com.nor35.photos.feature_album.presentation.album.state.PhotoState
import com.nor35.photos.feature_album.presenter.PresenterFixtures
import com.nor35.photos.feature_album.presenter.PresenterFixtures.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumViewModelTest {

    @MockK
    internal lateinit var mockGetAlbumUseCase: GetAlbumUseCase
    @MockK
    internal lateinit var mockGetPhotoUseCase: GetPhotoUseCase
    @MockK
    internal lateinit var mockReloadAllPhotosUseCase: ReloadAllPhotosUseCase
    @MockK
    internal lateinit var mockNavController: NavController

    private lateinit var viewModel: AlbumViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
    }

    @Test
    fun init_AlbumViewModel_fetches_PhotoStatel() {

        //given
        coEvery {
            mockGetAlbumUseCase.invoke()
        } returns(PresenterFixtures.getPhotoFlow())

        //when
        viewModel = AlbumViewModel(mockGetAlbumUseCase, mockGetPhotoUseCase,
            mockReloadAllPhotosUseCase, mockNavController)

        //then
        assertEquals(viewModel.liveData.getOrAwaitValue(),
            PhotoState(album = PresenterFixtures.getListPhoto())
        )
    }

    @Test
    fun getPhoto_fetches_PhotoState() {

        //given
        coEvery {
            mockGetAlbumUseCase.invoke()
        } returns flow { emit(Resource.Loading<List<Photo>>()) }
        coEvery {
            mockGetPhotoUseCase.invoke()
        } returns PresenterFixtures.getPhotoFlow()

        viewModel = AlbumViewModel(mockGetAlbumUseCase, mockGetPhotoUseCase,
            mockReloadAllPhotosUseCase, mockNavController)

        //when
        viewModel.getPhoto()

        //then
        assertEquals(viewModel.liveData.getOrAwaitValue(),
            PhotoState(album = PresenterFixtures.getListPhoto()))
    }
}