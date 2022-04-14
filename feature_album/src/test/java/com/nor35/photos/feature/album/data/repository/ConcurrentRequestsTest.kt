package com.nor35.photos.feature.album.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class ConcurrentRequestsTest {

    @Test
    fun atomicInteger_Async() {

        val n = 10

        val count = AtomicInteger()
        runBlocking {
            val tasks = List(n) { async(Dispatchers.IO) { count.addAndGet(longRunningTask()) } }
            tasks.awaitAll()

            assertEquals(n, count.get())
        }
    }

    suspend fun longRunningTask(): Int {
        delay(500)
        return 1
    }
}
