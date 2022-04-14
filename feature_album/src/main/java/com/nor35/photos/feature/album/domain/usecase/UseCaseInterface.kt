package com.nor35.photos.feature.album.domain.usecase

import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface UseCaseInterface {

    operator fun invoke(): Flow<Resource<List<Photo>>>
}
