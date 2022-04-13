package com.nor35.photos.feature_album.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.nor35.photos.feature_album.di.modules.FeatureAlbumDataModule
import com.nor35.photos.feature_album.di.modules.FeatureAlbumNetworkModule
import com.nor35.photos.feature_album.presentation.album.PhotosFragment
import com.nor35.photos.feature_album.presentation.photo_detail.PhotoDetailFragment
import dagger.BindsInstance
import dagger.Component

@FeatureAlbumScope
@Component(modules = [FeatureAlbumDataModule::class, FeatureAlbumNetworkModule::class])
interface FeatureAlbumComponent {

    fun inject(photosFragment: PhotosFragment)
    fun inject(photosFragment: PhotoDetailFragment)

    @Component.Builder
    interface AlbumComponentBuilder {
        fun buildAlbumComponent(): FeatureAlbumComponent

        @BindsInstance
        fun bindContext(context: Context): AlbumComponentBuilder

        @BindsInstance
        fun bindFragment(fragment: Fragment): AlbumComponentBuilder
    }
}
