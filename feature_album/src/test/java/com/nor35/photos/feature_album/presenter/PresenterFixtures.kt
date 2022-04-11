package com.nor35.photos.feature_album.presenter

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature_album.data.database.PhotoEntity
import com.nor35.photos.feature_album.domain.DomainFixtures
import com.nor35.photos.feature_album.domain.model.Photo
import com.nor35.photos.feature_album.domain.model.PhotoDetail
import com.nor35.photos.feature_album.presentation.album.state.PhotoState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object PresenterFixtures {

    val _imageUrl: String = "url"
    val _height: Int = 1
    val _width: Int = 2
    val _id: Long = 3

    internal fun getPhoto(
        imageUrl: String = _imageUrl,
        height: Int = _height,
        width: Int = _width,
        id: Long = _id
    ): Photo = Photo(id, imageUrl, width, height)

    internal fun getListPhoto() = listOf(getPhoto())

    internal fun getPhotoDetail(
        imageUrl: String = DomainFixtures._url,
        height: Int = DomainFixtures._height,
        width: Int = DomainFixtures._width,
        id: Long = DomainFixtures._id
    ): PhotoDetail = PhotoDetail(id, imageUrl, width, height)

    internal fun getPhotoFlow() = flow {
        emit(Resource.Loading<List<Photo>>())
        emit(Resource.Success<List<Photo>>(getListPhoto()))
    }

    internal fun getPhotoDetailFlow(): Flow<Resource<PhotoDetail>> = flow {
        emit(Resource.Loading<PhotoDetail>())
        emit(Resource.Success<PhotoDetail>(getPhotoDetail()))
    }

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


