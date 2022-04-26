package com.nor35.photos.feature.album.di

import android.content.Context
import com.nor35.photos.di.AppComponent
import com.nor35.photos.feature.album.di.modules.FeatureAlbumDataModule
import com.nor35.photos.feature.album.presentation.album.PhotosFragment
import com.nor35.photos.feature.album.presentation.photo.detail.PhotoDetailFragment
import dagger.BindsInstance
import dagger.Component

@FeatureAlbumScope
@Component(
    dependencies = [AppComponent::class], modules = [FeatureAlbumDataModule::class])
internal interface FeatureAlbumComponent {

    fun inject(photosFragment: PhotosFragment)
    fun inject(photosFragment: PhotoDetailFragment)

    @Component.Builder
    interface AlbumComponentBuilder {
        fun buildAlbumComponent(): FeatureAlbumComponent

        @BindsInstance
        fun bindContext(context: Context): AlbumComponentBuilder

        fun bindAppComponent(appComponent: AppComponent): AlbumComponentBuilder
    }
}
