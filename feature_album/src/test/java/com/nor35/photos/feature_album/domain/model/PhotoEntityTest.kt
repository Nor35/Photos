package com.nor35.photos.feature_album.domain.model

import com.nor35.photos.feature_album.data.database.toDomainModel
import com.nor35.photos.feature_album.data.database.toPhotoDetailDomainModel
import com.nor35.photos.feature_album.domain.DomainFixtures
import org.junit.Test

import org.junit.Assert.*


class PhotoEntityTest {

    @Test
    fun photoEntity_toDomainModel() {

        //given
        val photoEntity = DomainFixtures.getPhotoEntity()

        //when
        val domainModel = photoEntity.toDomainModel()

        //then
        assertEquals(domainModel.id, DomainFixtures._id)
        assertEquals(domainModel.imageUrl, DomainFixtures._url)
        assertEquals(domainModel.width, DomainFixtures._width)
        assertEquals(domainModel.height, DomainFixtures._height)
    }

    @Test
    fun photoEntity_toPhotoDetailDomainModel() {

        //given
        val photoEntity = DomainFixtures.getPhotoEntity()

        //when
        val photoDetailDomainModel = photoEntity.toPhotoDetailDomainModel()

        //then
        assertEquals(photoDetailDomainModel.id, DomainFixtures._id)
        assertEquals(photoDetailDomainModel.imageUrl, DomainFixtures._url)
        assertEquals(photoDetailDomainModel.width, DomainFixtures._width)
        assertEquals(photoDetailDomainModel.height, DomainFixtures._height)
    }
}