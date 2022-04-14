package com.nor35.photos.feature.album.data.remote

import com.nor35.photos.feature.album.data.remote.dto.LoremFlickrDto
import retrofit2.http.GET

interface PhotoApi {

    @GET("json/320/320/cat/")
    suspend fun getPhoto(): LoremFlickrDto
}
