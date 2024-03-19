package com.example.myhome.features.chat.repositories

import com.example.myhome.features.chat.ChatAddRequest
import com.example.myhome.features.chat.ChatListItemResponse
import com.example.myhome.features.chat.ChatUserListItemResponse
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun listReceiver(): Flow<List<ChatUserListItemResponse>>
    fun addChat(users: ChatAddRequest): Flow<ChatListItemResponse>
}