package com.nor35.photos.feature.photo.detail.presenter

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nor35.photos.domain.Resource
import com.nor35.photos.feature.photo.detail.domain.DomainFixtures
import com.nor35.photos.feature.photo.detail.domain.model.PhotoDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object PresenterFixtures {

    const val _imageUrl: String = "url"
    const val _height: Int = 1
    const val _width: Int = 2
    const val _id: Long = 3

    internal fun getPhotoDetail(
        imageUrl: String = DomainFixtures._url,
        height: Int = DomainFixtures._height,
        width: Int = DomainFixtures._width,
        id: Long = DomainFixtures._id
    ): PhotoDetail = PhotoDetail(id, imageUrl, width, height)

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
