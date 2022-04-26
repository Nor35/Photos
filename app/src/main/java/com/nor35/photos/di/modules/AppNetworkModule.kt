package com.nor35.photos.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nor35.photos.data.remote.PhotoApi
import com.nor35.photos.di.AppScope
import com.nor35.photos.domain.Constants
import com.nor35.photos.domain.Constants.MY_NETWORK_TIMEOUT
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
class AppNetworkModule {

    @Provides
    @AppScope
    fun providePhotoApi(gson: Gson, okHttpClient: OkHttpClient): PhotoApi {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PhotoApi::class.java)
    }

    @Provides
    @AppScope
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .readTimeout(MY_NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(MY_NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {

        Timber.plant(Timber.DebugTree())

        val interceptor = HttpLoggingInterceptor { message ->
            Timber.i(message)
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return interceptor
    }
}
