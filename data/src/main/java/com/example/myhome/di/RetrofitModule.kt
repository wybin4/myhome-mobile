package com.example.myhome.di

import android.content.Context
import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.auth.AuthAuthenticator
import com.example.myhome.features.auth.interceptors.AccessTokenInterceptor
import com.example.myhome.features.auth.interceptors.RefreshTokenInterceptor
import com.example.myhome.features.auth.services.AuthApiService
import com.example.myhome.features.auth.services.RefreshApiService
import com.example.myhome.features.charge.ChargeApiService
import com.example.myhome.features.chat.ChatApiService
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
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthenticatedClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TokenRefreshClient

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicClient

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    companion object {
        const val TIMEOUT = 10L
        const val BASE_URL = "https://personally-poetic-cattle.ngrok-free.app/api/"
    }

    @Provides
    @Singleton
    @AuthenticatedClient
    fun provideAccessOkHttpClient(
        @ApplicationContext context: Context,
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024L // 10MB
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    @TokenRefreshClient
    fun provideRefreshOkHttpClient(
        @ApplicationContext context: Context,
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024L
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .addInterceptor(refreshTokenInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    @PublicClient
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("Normal")
    fun provideRetrofit(@AuthenticatedClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(@PublicClient okHttpClient: OkHttpClient): AuthApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshApiService(@TokenRefreshClient okHttpClient: OkHttpClient): RefreshApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RefreshApiService::class.java)
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

    @Provides
    @Singleton
    fun provideChatApiService(@Named("Normal") retrofit: Retrofit): ChatApiService {
        return retrofit.create(ChatApiService::class.java)
    }

}