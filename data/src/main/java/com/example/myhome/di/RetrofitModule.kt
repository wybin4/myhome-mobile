package com.example.myhome.di

import android.content.Context
import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.charge.ChargeApiService
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
        private const val BASE_URL = "https://personally-poetic-cattle.ngrok-free.app/api/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024L // 10MB
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    @Named("Normal")
    fun provideNormalRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
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

    @Provides
    @Singleton
    fun provideChargeApiService(@Named("Normal") retrofit: Retrofit): ChargeApiService {
        return retrofit.create(ChargeApiService::class.java)
    }
}