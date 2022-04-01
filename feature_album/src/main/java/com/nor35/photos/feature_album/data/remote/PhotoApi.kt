package com.nor35.photos.feature_album.data.remote

import com.nor35.photos.feature_album.data.remote.dto.LoremFlickrDto
import retrofit2.http.GET

interface PhotoApi {

    @GET("json/200/200/cat/")
    suspend fun getPhoto(): LoremFlickrDto
}