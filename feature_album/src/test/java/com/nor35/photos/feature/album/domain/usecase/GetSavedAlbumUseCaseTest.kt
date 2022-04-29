package com.nor35.photos.feature.album.domain.usecase

import app.cash.turbine.test
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.DomainFixtures
import com.nor35.photos.feature.album.domain.repository.PhotoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetSavedAlbumUseCaseTest {

    @MockK
    internal lateinit var mockPhotoRepository: PhotoRepository

    private lateinit var useCase: GetSavedAlbumUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetSavedAlbumUseCase(mockPhotoRepository, DomainFixtures.getPhotoIdArray())
    }

    @Test
    fun invoke_fetches_ListPhoto_convert_toDomainModel() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto(DomainFixtures._id)
        } returns(DomainFixtures.getPhotoEntity())

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)

                repeat(DomainFixtures._numbersOfPhotoOnAlbum) {
                    val resultItem = awaitItem()
                    assertTrue(resultItem is Resource.Success<*>)
                    assertEquals(
                        listOf(DomainFixtures.getPhoto()),
                        (resultItem as Resource.Success<*>).data
                    )
                }

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_GetPhoto_return_null_getRandomPhotoFromDB_fetches_ListPhoto_convert_toDomainModel() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto(DomainFixtures._id)
        } returns(null)
        coEvery {
            mockPhotoRepository.getRandomPhotoFromDB()
        } returns(DomainFixtures.getPhotoEntity())

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)

                repeat(DomainFixtures._numbersOfPhotoOnAlbum) {
                    val resultItem = awaitItem()
                    assertTrue(resultItem is Resource.Success<*>)
                    assertEquals(
                        listOf(DomainFixtures.getPhoto()),
                        (resultItem as Resource.Success<*>).data
                    )
                }

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_GetPhoto_return_null_getRandomPhotoFromDB_return_null() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto(DomainFixtures._id)
        } returns(null)
        coEvery {
            mockPhotoRepository.getRandomPhotoFromDB()
        } returns(null)

        runBlocking {
            // when
            val result = useCase.invoke()

            // then
            result.test {
                assertTrue(awaitItem() is Resource.Loading<*>)
                repeat(DomainFixtures._numbersOfPhotoOnAlbum) {
                    assertTrue(awaitItem() is Resource.Error<*>)
                }

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_getPhoto_throws_Exception_getDbAlbumIfExist_return_data() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto(DomainFixtures._id)
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

                repeat(DomainFixtures._numbersOfPhotoOnAlbum) {
                    val resultItem = awaitItem()
                    assertTrue(resultItem is Resource.Success<*>)
                    assertEquals(
                        listOf(DomainFixtures.getPhoto()),
                        (resultItem as Resource.Success<*>).data
                    )
                }

                awaitComplete()
            }
        }
    }

    @Test
    fun invoke_getAlbum_throws_Exception_getRandomPhotoFromDB_return_null() {
        // given
        coEvery {
            mockPhotoRepository.getPhoto(DomainFixtures._id)
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

                repeat(DomainFixtures._numbersOfPhotoOnAlbum) {
                    assertTrue(awaitItem() is Resource.Error<*>)
                }

                awaitComplete()
            }
        }
    }
}
