package com.nor35.photos.feature.photo.detail.data.repository

import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.feature.photo.detail.data.DataFixtures
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PhotoRepositoryImplTest {

    @MockK
    internal lateinit var mockPhotoDao: PhotoDao

    private lateinit var photoRepositoryImpl: PhotoRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        photoRepositoryImpl = PhotoRepositoryImpl(mockPhotoDao)
    }

    @Test
    fun getPhoto_byId_fetches_PhotoEntity() {

        // given
        coEvery {
            mockPhotoDao.getPhoto(DataFixtures._id)
        } returns(DataFixtures.getPhotoEntity())

        // when
        val result = runBlocking { photoRepositoryImpl.getPhoto(DataFixtures._id) }

        // then
        Assert.assertNotNull(result)
        assertEquals(result!!.id, DataFixtures._id)
        assertEquals(result.width, DataFixtures._width)
        assertEquals(result.height, DataFixtures._height)
        assertEquals(result.url, DataFixtures._file)
    }
}
