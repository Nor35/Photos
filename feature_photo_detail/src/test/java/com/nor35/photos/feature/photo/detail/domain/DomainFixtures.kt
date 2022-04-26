package com.nor35.photos.feature.photo.detail.domain

import com.nor35.photos.data.database.PhotoEntity
import com.nor35.photos.feature.photo.detail.domain.model.PhotoDetail

object DomainFixtures {

    const val _url: String = "url"
    const val _height: Int = 1
    const val _width: Int = 2
    const val _id: Long = 3
    const val _numbersOfPhotoOnAlbum = 10

    internal fun getPhotoEntity(
        url: String = _url,
        height: Int = _height,
        width: Int = _width,
        id: Long = _id
    ): PhotoEntity {

        val photoEntity = PhotoEntity(url, width, height)
        photoEntity.id = id
        return photoEntity
    }

    internal fun getPhotoDetail(
        imageUrl: String = _url,
        height: Int = _height,
        width: Int = _width,
        id: Long = _id
    ): PhotoDetail = PhotoDetail(id, imageUrl, width, height)
}
