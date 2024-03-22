package com.example.myhome.features.chat.repositories

import com.example.myhome.features.chat.dtos.ChatAddRequest
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.ChatUserListItemResponse
import com.example.myhome.features.chat.dtos.MessageAddRequest
import com.example.myhome.features.chat.dtos.MessageListItemResponse
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun listMessage(chatId: Int): Flow<List<MessageListItemResponse>>
    fun listReceiver(): Flow<List<ChatUserListItemResponse>>
    fun addChat(users: ChatAddRequest): Flow<ChatListItemResponse>
}