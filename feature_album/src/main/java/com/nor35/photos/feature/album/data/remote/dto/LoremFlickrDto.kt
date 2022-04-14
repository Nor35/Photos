package com.nor35.photos.feature.album.data.remote.dto

import com.nor35.photos.feature.album.data.database.PhotoEntity

data class LoremFlickrDto(
    val file: String,
    val filter: String,
    val height: Int,
    val license: String,
    val owner: String,
    val tagMode: String,
    val tags: String,
    val width: Int
)

fun LoremFlickrDto.toDataBaseModel() = PhotoEntity(
    url = file,
    width = width,
    height = height
)
