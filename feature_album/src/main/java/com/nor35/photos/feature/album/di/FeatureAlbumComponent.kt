package com.nor35.photos.feature.album.di

import com.nor35.photos.di.AppComponent
import com.nor35.photos.feature.album.di.modules.FeatureAlbumDataModule
import com.nor35.photos.feature.album.presentation.album.PhotosFragment
import dagger.Component

@FeatureAlbumScope
@Component(
    dependencies = [AppComponent::class], modules = [FeatureAlbumDataModule::class]
)
internal interface FeatureAlbumComponent {

    fun inject(photosFragment: PhotosFragment)

    @Component.Builder
    interface AlbumComponentBuilder {
        fun buildAlbumComponent(): FeatureAlbumComponent

        fun bindAppComponent(appComponent: AppComponent): AlbumComponentBuilder
    }
}
