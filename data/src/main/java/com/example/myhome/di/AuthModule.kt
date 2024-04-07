package com.example.myhome.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.myhome.features.auth.JwtTokenDataStore
import com.example.myhome.features.auth.JwtTokenStore
import com.example.myhome.features.auth.repositories.AuthRepository
import com.example.myhome.features.auth.repositories.AuthRepositoryImpl
import com.example.myhome.features.auth.services.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    companion object {
        const val AUTH_PREFERENCES = "auth_preferences"
    }

    @Provides
    @Singleton
    fun provideJwtTokenManager(dataStore: DataStore<Preferences>): JwtTokenStore {
        return JwtTokenDataStore(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { appContext.preferencesDataStoreFile(AUTH_PREFERENCES) }
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService,
        jwtTokenStore: JwtTokenStore
    ): AuthRepository {
        return AuthRepositoryImpl(authApiService, jwtTokenStore)
    }
}