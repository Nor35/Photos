package com.nor35.photos.di.modules

import android.content.Context
import androidx.room.Room
import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.data.database.PhotoDatabase
import com.nor35.photos.di.AppScope
import com.nor35.photos.domain.Constants.PHOTO_DATABASE_NAME
import dagger.Module
import dagger.Provides

@Module
class AppDataModule {

    @Provides
    @AppScope
    fun providePhotoDatabase(context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            PHOTO_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @AppScope
    fun providePhotoDao(db: PhotoDatabase): PhotoDao {
        return db.getPhotoDao()
    }
}
