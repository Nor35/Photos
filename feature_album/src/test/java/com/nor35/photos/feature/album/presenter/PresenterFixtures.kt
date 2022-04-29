package com.nor35.photos.feature.album.presenter

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.album.domain.DomainFixtures
import com.nor35.photos.feature.album.domain.model.Photo
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object PresenterFixtures {

    const val _imageUrl: String = "url"
    const val _height: Int = 1
    const val _width: Int = 2
    const val _id: Long = 3
    const val _numbersOfPhotoOnAlbum = 10

    internal fun getPhoto(
        imageUrl: String = _imageUrl,
        height: Int = _height,
        width: Int = _width,
        id: Long = _id
    ): Photo = Photo(id, imageUrl, width, height)

    internal fun getListPhoto() = listOf(getPhoto())

    internal fun getPhotoFlow() = flow {
        emit(Resource.Loading<List<Photo>>())
        emit(Resource.Success<List<Photo>>(getListPhoto()))
    }

    internal fun getPhotoIdArray(): LongArray = LongArray(_numbersOfPhotoOnAlbum) { DomainFixtures._id }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        try {
            afterObserve.invoke()

            // Don't wait indefinitely if the LiveData is not set.
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            this.removeObserver(observer)
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}
