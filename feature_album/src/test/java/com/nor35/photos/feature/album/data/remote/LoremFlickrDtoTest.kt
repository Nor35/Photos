package com.nor35.photos.feature.album.data.remote

import com.nor35.photos.feature.album.data.DataFixtures
import com.nor35.photos.feature.album.data.remote.dto.toDataBaseModel
import org.junit.Assert.assertEquals
import org.junit.Test

class LoremFlickrDtoTest {

    @Test
    fun loremFlickrDto_toDataBaseModel() {

        // given
        val loremFlickrDto = DataFixtures.getLoremFlickrDto()

        // when
        val dataBaseModel = loremFlickrDto.toDataBaseModel()

        // then
        assertEquals(dataBaseModel.url, DataFixtures._file)
        assertEquals(dataBaseModel.width, DataFixtures._width)
        assertEquals(dataBaseModel.height, DataFixtures._height)
    }
}
