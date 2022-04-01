package com.nor35.photos

import com.nor35.photos.feature_album.data.repository.PhotoRepositoryImp
import com.nor35.photos.feature_album.di.DaggerFeatureAlbumComponent
import com.nor35.photos.feature_album.presentation.album_list.AlbumViewModel
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ConcurrentRequestsTest {

    @Test
    fun AtomicInteger_Async() {

        val n = 10

        val count = AtomicInteger()
        runBlocking {
            val tasks = List(n){ async(Dispatchers.IO) { count.addAndGet(longRunningTask()) } }
            tasks.awaitAll()


            assertEquals(n, count.get())
        }
    }

    suspend fun longRunningTask(): Int{
        delay(500)
        return 1
    }


}