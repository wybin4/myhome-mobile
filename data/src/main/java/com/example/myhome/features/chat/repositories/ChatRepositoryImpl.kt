package com.example.myhome.features.chat.repositories

import com.example.myhome.features.chat.ChatApiService
import com.example.myhome.features.chat.dtos.ChatAddRequest
import com.example.myhome.features.chat.dtos.ChatAddRequestItem
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.ChatUserListItemResponse
import com.example.myhome.features.chat.dtos.MessageListRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl(
    private val chatApiService: ChatApiService
): ChatRepository {
    override fun listMessage(chatId: String) = flow {
        val request = MessageListRequest(chatId = chatId)
        val response = chatApiService.listMessage(request)
        emit(response.messages)
    }

    override fun listReceiver(): Flow<List<ChatUserListItemResponse>> = flow {
        val response = chatApiService.listReceiver()
        emit(response.receivers)
    }

    override fun addChat(user: ChatAddRequestItem): Flow<ChatListItemResponse> = flow {
        val request = ChatAddRequest(
            users = listOf(user)
        )
        val response = chatApiService.addChat(request)
        emit(response.chat)
    }
}
