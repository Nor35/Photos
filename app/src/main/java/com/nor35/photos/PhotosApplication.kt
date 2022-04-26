package com.nor35.photos

import android.app.Application
import com.nor35.photos.di.AppComponent
import com.nor35.photos.di.DaggerAppComponent

class PhotosApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .bindContext(this)
            .buildAlbumComponent()
    }
}
