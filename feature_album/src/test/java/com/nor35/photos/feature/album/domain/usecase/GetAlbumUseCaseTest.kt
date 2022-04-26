package com.nor35.photos.feature.album.domain.usecase

import app.cash.turbine.test
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.DomainFixtures
import com.nor35.photos.domain.repository.PhotoRepository
import com.nor35.photos.feature.album.domain.model.toDomainModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAlbumUseCaseTest {

    @MockK
    internal lateinit var mockPhotoRepository: PhotoRepository

    private lateinit var useCase: GetAlbumUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetAlbumUseCase(mockPhotoRepository)
    }

    @Test
    fun invoke_fetches_ListPhotoEntity_convert_toDomainModel() {
        // given
        coEvery {
            mockPhotoRepository.getAlbum()
        } returns(DomainFixtures.getAlbum())

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)

                val resultItem = awaitItem()
                assertTrue(resultItem is Resource.Success<*>)
                assertEquals(
                    DomainFixtures.getAlbum().map { it.toDomainModel() },
                    (resultItem as Resource.Success<*>).data
                )

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_getAlbum_throws_Exception_getAlbumFromDB_return_data() {
        // given
        coEvery {
            mockPhotoRepository.getAlbum()
        } throws Exception()
        coEvery {
            mockPhotoRepository.getAlbumFromDB()
        } returns DomainFixtures.getAlbum()

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)

                val resultItem = awaitItem()
                assertTrue(resultItem is Resource.Success<*>)
                assertEquals(
                    DomainFixtures.getAlbum().map { it.toDomainModel() },
                    (resultItem as Resource.Success<*>).data
                )

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_getAlbum_throws_Exception_getRandomPhotoFromDB_return_null() {
        // given
        coEvery {
            mockPhotoRepository.getAlbum()
        } throws Exception()
        coEvery {
            mockPhotoRepository.getAlbumFromDB()
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
