package com.nor35.photos

import android.app.Application
import com.nor35.photos.di.DaggerAppComponent

class PhotosApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent
            .builder()
            .bindContext(this)
            .buildAlbumComponent()
    }
}
