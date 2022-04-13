package com.nor35.photos.feature_album.domain.use_case

import app.cash.turbine.test
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.domain.DomainFixtures
import com.nor35.photos.feature_album.domain.repository.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPhotoUseCaseTest {

    @MockK
    internal lateinit var mockPhotoRepository: PhotoRepository

    private lateinit var useCase: GetPhotoUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetPhotoUseCase(mockPhotoRepository)
    }

    @Test
    fun invoke_fetches_ListPhoto_convert_toDomainModel() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto()
        } returns(DomainFixtures.getPhotoEntity())

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)

                val resultItem = awaitItem()
                assertTrue(resultItem is Resource.Success<*>)
                assertEquals(listOf(DomainFixtures.getPhoto()), (resultItem as Resource.Success<*>).data)

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_getPhoto_throws_Exception_getDbAlbumIfExist_return_data() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto()
        } throws Exception()
        coEvery {
            mockPhotoRepository.getRandomPhotoFromDB()
        } returns DomainFixtures.getPhotoEntity()

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)

                val resultItem = awaitItem()
                assertTrue(resultItem is Resource.Success<*>)
                assertEquals(listOf(DomainFixtures.getPhoto()), (resultItem as Resource.Success<*>).data)

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_getPhoto_throws_Exception_getDbAlbumIfExist_return_null() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto()
        } throws Exception()
        coEvery {
            mockPhotoRepository.getRandomPhotoFromDB()
        } returns null

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)
                assertTrue(awaitItem() is Resource.Error<*>)
                awaitComplete()
            }
        }
    }
}
