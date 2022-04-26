package com.nor35.photos.feature.photo.detail.di.modules

import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.feature.photo.detail.data.repository.PhotoRepositoryImpl
import com.nor35.photos.feature.photo.detail.di.FeaturePhotoDetailScope
import com.nor35.photos.feature.photo.detail.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides

@Module
class FeaturePhotoDetailModule {

    @Provides
    @FeaturePhotoDetailScope
    fun providePhotoRepository(
        photoDao: PhotoDao
    ): PhotoRepository {
        return PhotoRepositoryImpl(photoDao)
    }
}
