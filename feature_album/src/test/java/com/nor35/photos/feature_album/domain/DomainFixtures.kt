package com.nor35.photos.feature_album.domain

import com.nor35.photos.feature_album.data.database.PhotoEntity
import com.nor35.photos.feature_album.domain.model.Photo
import com.nor35.photos.feature_album.domain.model.PhotoDetail

object DomainFixtures {

    val _url: String = "url"
    val _height: Int = 1
    val _width: Int = 2
    val _id: Long = 3
    val _numbersOfPhotoOnAlbum = 10

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

    internal fun getPhoto(
        imageUrl: String = _url,
        height: Int = _height,
        width: Int = _width,
        id: Long = _id
    ): Photo = Photo(id, imageUrl, width, height)

    internal fun getPhotoDetail(
        imageUrl: String = _url,
        height: Int = _height,
        width: Int = _width,
        id: Long = _id
    ): PhotoDetail = PhotoDetail(id, imageUrl, width, height)

    internal fun getAlbum(): List<PhotoEntity> = List(_numbersOfPhotoOnAlbum){ getPhotoEntity() }
}


