package com.nor35.photos.feature.photo.detail.di

import com.nor35.photos.di.AppComponent
import com.nor35.photos.feature.photo.detail.di.modules.FeaturePhotoDetailModule
import com.nor35.photos.feature.photo.detail.presentation.photo.detail.PhotoDetailFragment
import dagger.Component

@FeaturePhotoDetailScope
@Component(
    dependencies = [AppComponent::class], modules = [FeaturePhotoDetailModule::class]
)
internal interface FeaturePhotoDetailComponent {

    fun inject(photosFragment: PhotoDetailFragment)

    @Component.Builder
    interface AlbumComponentBuilder {
        fun buildAlbumComponent(): FeaturePhotoDetailComponent

        fun bindAppComponent(appComponent: AppComponent): AlbumComponentBuilder
    }
}
