package com.nor35.photos.feature.album.domain.model

import com.nor35.photos.feature.album.domain.DomainFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoEntityTest {

    @Test
    fun photoEntity_toDomainModel() {

        // given
        val photoEntity = DomainFixtures.getPhotoEntity()

        // when
        val domainModel = photoEntity.toDomainModel()

        // then
        assertEquals(domainModel.id, DomainFixtures._id)
        assertEquals(domainModel.imageUrl, DomainFixtures._url)
        assertEquals(domainModel.width, DomainFixtures._width)
        assertEquals(domainModel.height, DomainFixtures._height)
    }
}
