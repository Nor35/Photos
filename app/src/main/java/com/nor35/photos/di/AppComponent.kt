package com.nor35.photos.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.nor35.photos.data.database.PhotoDao
import com.nor35.photos.data.remote.PhotoApi
import com.nor35.photos.di.modules.AppDataModule
import com.nor35.photos.di.modules.AppNetworkModule
import com.nor35.photos.domain.repository.PhotoRepository
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppDataModule::class, AppNetworkModule::class])
interface AppComponent {

    fun photoDao(): PhotoDao
    fun photoApi(): PhotoApi

    @Component.Builder
    interface AlbumComponentBuilder {
        fun buildAlbumComponent(): AppComponent

        @BindsInstance
        fun bindContext(context: Context): AlbumComponentBuilder
    }
}
