package com.example.myhome.di

import android.content.Context
import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.meter.MeterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    companion object {
        private const val BASE_URL = "https://wealthy-barnacle-secure.ngrok-free.app/api/"
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
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
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

    @Provides
    @Singleton
    fun provideCommonApiService(@Named("Normal") retrofit: Retrofit): CommonApiService {
        return retrofit.create(CommonApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppealApiService(@Named("Normal") retrofit: Retrofit): AppealApiService {
        return retrofit.create(AppealApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventApiService(@Named("Normal") retrofit: Retrofit): EventApiService {
        return retrofit.create(EventApiService::class.java)
    }
}