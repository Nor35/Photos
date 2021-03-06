package com.nor35.photos.feature.album.presenter.album

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nor35.photos.feature.album.presentation.album.recyclerview.PhotoAdapter
import com.nor35.photos.feature.album.presenter.PresenterFixtures
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoAdapterTest {

    private var photoAdapter: PhotoAdapter = PhotoAdapter()

    @Test
    fun addPhotos_check_itemCounts() {

        assertEquals(photoAdapter.itemCount, 0)

        // when
        photoAdapter.addPhotos(PresenterFixtures.getListPhoto())

        // then
        assertEquals(photoAdapter.itemCount, 1)
    }

    @Test
    fun deletePhotos_check_itemCounts() {

        assertEquals(photoAdapter.itemCount, 0)

        // when
        photoAdapter.addPhotos(PresenterFixtures.getListPhoto())

        // then
        assertEquals(photoAdapter.itemCount, 1)

        // when
        photoAdapter.deletePhotos()

        // then
        assertEquals(photoAdapter.itemCount, 0)
    }
}
