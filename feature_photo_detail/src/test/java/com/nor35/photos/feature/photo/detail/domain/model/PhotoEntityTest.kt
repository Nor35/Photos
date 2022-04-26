package com.nor35.photos.feature.photo.detail.domain.model

import com.nor35.photos.feature.photo.detail.domain.DomainFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoEntityTest {

    @Test
    fun photoEntity_toPhotoDetailDomainModel() {

        // given
        val photoEntity = DomainFixtures.getPhotoEntity()

        // when
        val photoDetailDomainModel = photoEntity.toPhotoDetailDomainModel()

        // then
        assertEquals(photoDetailDomainModel.id, DomainFixtures._id)
        assertEquals(photoDetailDomainModel.imageUrl, DomainFixtures._url)
        assertEquals(photoDetailDomainModel.width, DomainFixtures._width)
        assertEquals(photoDetailDomainModel.height, DomainFixtures._height)
    }
}
