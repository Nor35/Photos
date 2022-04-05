package com.nor35.photos.feature_album.domain.use_case

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface UseCaseInterface {

    abstract operator fun invoke(): Flow<Resource<List<Photo>>>

}