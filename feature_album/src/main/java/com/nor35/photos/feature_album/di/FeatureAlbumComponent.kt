package com.nor35.photos.feature_album.di

import android.content.Context
import com.nor35.photos.feature_album.di.modules.FeatureAlbumDataModule
import com.nor35.photos.feature_album.di.modules.FeatureAlbumNetworkModule
import com.nor35.photos.feature_album.presentation.album_list.PhotosFragment
import dagger.BindsInstance
import dagger.Component

@FeatureAlbumScope
@Component(modules = [FeatureAlbumDataModule::class, FeatureAlbumNetworkModule::class])
interface FeatureAlbumComponent {

    fun inject(photosFragment: PhotosFragment)

    @Component.Builder
    interface AlbumComponentBuilder{
        fun buildAlbumComponent(): FeatureAlbumComponent

        @BindsInstance
        fun bindContext(context: Context): AlbumComponentBuilder
    }
}