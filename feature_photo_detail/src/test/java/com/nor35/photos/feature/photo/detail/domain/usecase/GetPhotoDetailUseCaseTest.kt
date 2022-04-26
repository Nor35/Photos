package com.nor35.photos.feature.photo.detail.domain.usecase

import app.cash.turbine.test
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.photo.detail.domain.DomainFixtures
import com.nor35.photos.feature.photo.detail.domain.repository.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPhotoDetailUseCaseTest {

    @MockK
    internal lateinit var mockPhotoRepository: PhotoRepository

    private lateinit var useCase: GetPhotoDetailUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetPhotoDetailUseCase(mockPhotoRepository)
    }

    @Test
    fun invoke_getPhoto_fetches_ListPhoto_convert_toPhotoDetailDomainModel() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto(DomainFixtures._id)
        } returns(DomainFixtures.getPhotoEntity())

        runBlocking {
            // when
            val result = useCase.invoke(DomainFixtures._id)

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)

                val resultItem = awaitItem()
                assertTrue(resultItem is Resource.Success<*>)
                assertEquals(DomainFixtures.getPhotoDetail(), (resultItem as Resource.Success<*>).data)

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_getPhoto_throws_Exception() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto(DomainFixtures._id)
        } throws Exception()

        runBlocking {
            // when
            val result = useCase.invoke(DomainFixtures._id)

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)
                assertTrue(awaitItem() is Resource.Error<*>)

                awaitComplete()
            }
        }
    }
}
