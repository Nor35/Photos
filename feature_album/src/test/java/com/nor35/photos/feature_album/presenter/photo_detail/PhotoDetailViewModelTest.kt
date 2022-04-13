package com.nor35.photos.feature_album.presenter.photo_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nor35.photos.feature_album.domain.use_case.GetPhotoDetailUseCase
import com.nor35.photos.feature_album.presentation.photo_detail.PhotoDetailViewModel
import com.nor35.photos.feature_album.presentation.photo_detail.state.PhotoDetailState
import com.nor35.photos.feature_album.presenter.PresenterFixtures
import com.nor35.photos.feature_album.presenter.PresenterFixtures.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDetailViewModelTest {

    @MockK
    internal lateinit var getPhotoDetailUseCase: GetPhotoDetailUseCase

    private lateinit var viewModel: PhotoDetailViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = PhotoDetailViewModel(getPhotoDetailUseCase)
    }

    @Test
    fun getPhoto_fetches_PhotoDetailState() {

        // given
        coEvery {
            getPhotoDetailUseCase.invoke(PresenterFixtures._id)
        } returns(PresenterFixtures.getPhotoDetailFlow())

        // when
        viewModel.getPhoto(PresenterFixtures._id)

        // then
        assertEquals(
            viewModel.liveData.getOrAwaitValue(),
            PhotoDetailState(photoDetail = PresenterFixtures.getPhotoDetail())
        )
    }
}
