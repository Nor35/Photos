package com.nor35.photos.feature.album.data

import com.nor35.photos.feature.album.data.database.PhotoEntity
import com.nor35.photos.feature.album.data.remote.dto.LoremFlickrDto

object DataFixtures {

    const val _file: String = "file"
    const val _filter: String = "filter"
    const val _height: Int = 1
    const val _license: String = "license"
    const val _owner: String = "owner"
    const val _tagMode: String = "tagMode"
    const val _tags: String = "tags"
    const val _width: Int = 2
    const val _id: Long = 3
    const val _numbersOfPhotoOnAlbum = 10

    internal fun getLoremFlickrDto(
        file: String = _file,
        filter: String = _filter,
        height: Int = _height,
        license: String = _license,
        owner: String = _owner,
        tagMode: String = _tagMode,
        tags: String = _tags,
        width: Int = _width
    ): LoremFlickrDto =
        LoremFlickrDto(file, filter, height, license, owner, tagMode, tags, width)

    internal fun getPhotoEntity(
        url: String = _file,
        height: Int = _height,
        width: Int = _width,
        id: Long = _id
    ): PhotoEntity {

        val photoEntity = PhotoEntity(url, width, height)
        photoEntity.id = id
        return photoEntity
    }

    internal fun getAlbum(): List<PhotoEntity> = List(_numbersOfPhotoOnAlbum) { getPhotoEntity() }
}
