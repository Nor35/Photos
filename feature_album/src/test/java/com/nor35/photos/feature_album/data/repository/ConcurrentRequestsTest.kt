package com.nor35.photos.feature_album.data.repository

import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.atomic.AtomicInteger

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