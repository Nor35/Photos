package com.nor35.photos.feature_album.di.modules

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.nor35.photos.domain.Constants.PHOTO_DATABASE_NAME
import com.nor35.photos.feature_album.data.database.PhotoDao
import com.nor35.photos.feature_album.data.database.PhotoDatabase
import com.nor35.photos.feature_album.data.remote.PhotoApi
import com.nor35.photos.feature_album.data.repository.PhotoRepositoryImpl
import com.nor35.photos.feature_album.di.FeatureAlbumScope
import com.nor35.photos.feature_album.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides


@Module
class FeatureAlbumDataModule {

    @Provides
    @FeatureAlbumScope
    fun providePhotoDatabase(context: Context): PhotoDatabase{
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            PHOTO_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @FeatureAlbumScope
    fun providePhotoDao(db: PhotoDatabase): PhotoDao{
        return db.getPhotoDao()
    }

    @Provides
    @FeatureAlbumScope
    fun providePhotoRepository(photoApi: PhotoApi
                               ,photoDao: PhotoDao
    ): PhotoRepository {
        return PhotoRepositoryImpl(photoApi
            , photoDao
        )
    }

    @Provides
    @FeatureAlbumScope
    fun provideNavController(fragment: Fragment): NavController {
        return fragment.findNavController()
    }

}