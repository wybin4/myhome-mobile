package com.example.myhome.di

import android.content.Context
import com.example.myhome.appeal.services.AppealApiService
import com.example.myhome.common.services.ApartmentApiService
import com.example.myhome.common.services.SubscriberApiService
import com.example.myhome.common.services.TypeOfServiceApiService
import com.example.myhome.meter.services.MeterApiService
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
        private const val BASE_URL = "https://6b84-95-25-65-16.ngrok-free.app/api/"
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
    @Provides
    @Singleton
    fun provideApartmentApiService(@Named("Normal") retrofit: Retrofit): ApartmentApiService {
        return retrofit.create(ApartmentApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideTypeOfServiceApiService(@Named("Normal") retrofit: Retrofit): TypeOfServiceApiService {
        return retrofit.create(TypeOfServiceApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideSubscriberApiService(@Named("Normal") retrofit: Retrofit): SubscriberApiService {
        return retrofit.create(SubscriberApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideAppealApiService(@Named("Normal") retrofit: Retrofit): AppealApiService {
        return retrofit.create(AppealApiService::class.java)
    }
}