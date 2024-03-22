package com.example.myhome.di

import com.example.myhome.features.chat.ChatApiService
import com.example.myhome.features.chat.repositories.ChatRepository
import com.example.myhome.features.chat.repositories.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChatDataModule {
    @Provides
    @Singleton
    fun provideChatRepository(
        chatApiService: ChatApiService
    ): ChatRepository {
        return ChatRepositoryImpl(chatApiService)
    }

}