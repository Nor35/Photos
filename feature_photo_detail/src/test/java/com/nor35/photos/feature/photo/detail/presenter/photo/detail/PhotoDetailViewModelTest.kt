package com.nor35.photos.feature.photo.detail.presenter.photo.detail

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nor35.photos.feature.photo.detail.domain.usecase.GetPhotoDetailUseCase
import com.nor35.photos.feature.photo.detail.presentation.photo.detail.PhotoDetailViewModel
import com.nor35.photos.feature.photo.detail.presentation.photo.detail.state.PhotoDetailState
import com.nor35.photos.feature.photo.detail.presenter.PresenterFixtures
import com.nor35.photos.feature.photo.detail.presenter.PresenterFixtures.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDetailViewModelTest {

    @MockK
    internal lateinit var getPhotoDetailUseCase: GetPhotoDetailUseCase

    private lateinit var viewModel: PhotoDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getPhoto_fetches_PhotoDetailState() {

        // given
        coEvery {
            getPhotoDetailUseCase.invoke(PresenterFixtures._id)
        } returns(PresenterFixtures.getPhotoDetailFlow())

        // when
        viewModel = PhotoDetailViewModel(getPhotoDetailUseCase, PresenterFixtures._id)

        // then
        assertEquals(
            viewModel.liveData.getOrAwaitValue(),
            PhotoDetailState(photoDetail = PresenterFixtures.getPhotoDetail())
        )
    }
}
