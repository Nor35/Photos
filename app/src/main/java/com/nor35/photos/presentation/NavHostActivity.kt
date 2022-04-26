package com.nor35.photos.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.nor35.photos.R
import com.nor35.photos.presentation.routing.AlbumFragmentRouterSource
import com.nor35.photos.presentation.routing.RouterSources

class NavHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        initRouting()
    }

    private fun initRouting() {
        RouterSources.albumFragmentRouterSource = object : AlbumFragmentRouterSource {
            override fun moveToPhotoDetail(photoId: Long) {

                val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
                    as NavHostFragment
                val navController = navHostFragment.navController

                val bundle = Bundle()
                bundle.putLong(resources.getString(R.string.photoId), photoId)

                navController.navigate(R.id.featurePhotoDetailNavGraph, bundle)
            }
        }
    }
}
