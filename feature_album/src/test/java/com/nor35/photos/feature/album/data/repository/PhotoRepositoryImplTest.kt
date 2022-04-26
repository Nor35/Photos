package com.nor35.photos.feature.album.data.repository

import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.data.remote.PhotoApi
import com.nor35.photos.data.remote.dto.toDataBaseModel
import com.nor35.photos.domain.Constants
import com.nor35.photos.feature.album.data.DataFixtures
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PhotoRepositoryImplTest {

    @MockK
    internal lateinit var mockPhotoApi: PhotoApi

    @MockK
    internal lateinit var mockPhotoDao: PhotoDao

    private lateinit var photoRepositoryImpl: PhotoRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        photoRepositoryImpl = PhotoRepositoryImpl(mockPhotoApi, mockPhotoDao)
    }

    @Test
    fun getPhoto_fetches_LoremFlickrDto_convert_toDataBaseModel() {
        // given
        coEvery {
            mockPhotoApi.getPhoto()
        } returns(DataFixtures.getLoremFlickrDto())
        coEvery {
            mockPhotoDao.insertPhoto(DataFixtures.getLoremFlickrDto().toDataBaseModel())
        } returns(3)

        // when
        val result = runBlocking { photoRepositoryImpl.getPhoto() }

        // then
        assertEquals(result.width, DataFixtures._width)
        assertEquals(result.height, DataFixtures._height)
        assertEquals(result.url, DataFixtures._file)
    }

    @Test
    fun getAlbum_fetches_List_PhotoEntity() {
        // given
        coEvery {
            mockPhotoApi.getPhoto()
        } returns(DataFixtures.getLoremFlickrDto())
        coEvery {
            mockPhotoDao.insertPhoto(DataFixtures.getLoremFlickrDto().toDataBaseModel())
        } returns(DataFixtures._id)

        // when
        val result = runBlocking { photoRepositoryImpl.getAlbum() }

        // then
        assertEquals(result.size, Constants.NUMBER_OF_PHOTOS_ON_PAGE)
        for (photoEntity in result) {
            assertEquals(photoEntity.width, DataFixtures._width)
            assertEquals(photoEntity.height, DataFixtures._height)
            assertEquals(photoEntity.url, DataFixtures._file)
            assertEquals(photoEntity.id, DataFixtures._id)
        }
    }

    @Test
    fun getRandomPhotoFromDB_fetches_PhotoEntity() {

        // given
        coEvery {
            mockPhotoDao.getRandomPhoto()
        } returns(DataFixtures.getPhotoEntity())

        // when
        val result = runBlocking { photoRepositoryImpl.getRandomPhotoFromDB() }

        // then
        assertEquals(result.id, DataFixtures._id)
        assertEquals(result.width, DataFixtures._width)
        assertEquals(result.height, DataFixtures._height)
        assertEquals(result.url, DataFixtures._file)
    }

    @Test
    fun getAlbumFromDB_fetches_PhotoEntity() {

        // given
        coEvery {
            mockPhotoDao.getAlbumFromDB()
        } returns(DataFixtures.getAlbum())

        // when
        val result = runBlocking { photoRepositoryImpl.getAlbumFromDB() }

        // then
        assertEquals(result.size, DataFixtures._numbersOfPhotoOnAlbum)
        for (photoEntity in result) {
            assertEquals(photoEntity.width, DataFixtures._width)
            assertEquals(photoEntity.height, DataFixtures._height)
            assertEquals(photoEntity.url, DataFixtures._file)
            assertEquals(photoEntity.id, DataFixtures._id)
        }
    }
}
