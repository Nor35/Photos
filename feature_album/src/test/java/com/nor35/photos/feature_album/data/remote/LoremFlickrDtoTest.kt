package com.nor35.photos.feature_album.data.remote

import com.nor35.photos.feature_album.data.DataFixtures
import com.nor35.photos.feature_album.data.remote.dto.toDataBaseModel
import org.junit.Test

import org.junit.Assert.*


class LoremFlickrDtoTest {

    @Test
    fun loremFlickrDto_toDataBaseModel() {

        //given
        val loremFlickrDto = DataFixtures.getLoremFlickrDto()

        //when
        val dataBaseModel = loremFlickrDto.toDataBaseModel()

        //then
        assertEquals(dataBaseModel.url, DataFixtures._file)
        assertEquals(dataBaseModel.width, DataFixtures._width)
        assertEquals(dataBaseModel.height, DataFixtures._height)
    }
}