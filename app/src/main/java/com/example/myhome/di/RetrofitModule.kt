package com.example.myhome.di

import android.content.Context
import android.util.Log
import com.example.myhome.data.services.MeterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    companion object {
        private const val BASE_URL = "https://87a0-95-25-63-200.ngrok-free.app/api/"
    }
    @Provides
    @Singleton
    @Named("Normal")
    fun provideRetrofit(
        @ApplicationContext context: Context
    ): Retrofit {
        val cacheSize = 10 * 1024 * 1024L // 10MB
        val cache = Cache(context.cacheDir, cacheSize)
        val okHttpClient = OkHttpClient.Builder()
//            .addNetworkInterceptor { chain ->
//
//            }
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideMeterApiService(@Named("Normal") retrofit: Retrofit): MeterApiService {
        return retrofit.create(MeterApiService::class.java)
    }
}