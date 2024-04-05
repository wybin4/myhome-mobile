package com.example.myhome.features.chat.repositories

import com.example.myhome.features.chat.dtos.ChatAddRequestItem
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.ChatUserListItemResponse
import com.example.myhome.features.chat.dtos.MessageListItemResponse
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun listMessage(chatId: String): Flow<List<MessageListItemResponse>>
    fun listReceiver(): Flow<List<ChatUserListItemResponse>>
    fun addChat(user: ChatAddRequestItem): Flow<ChatListItemResponse>
}