package com.nor35.photos.feature.album.di.modules

import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.data.remote.PhotoApi
import com.nor35.photos.feature.album.data.repository.PhotoRepositoryImpl
import com.nor35.photos.feature.album.di.FeatureAlbumScope
import com.nor35.photos.feature.album.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides

@Module
class FeatureAlbumDataModule {

    @Provides
    @FeatureAlbumScope
    fun providePhotoRepository(
        photoApi: PhotoApi,
        photoDao: PhotoDao
    ): PhotoRepository {
        return PhotoRepositoryImpl(
            photoApi, photoDao
        )
    }
}
