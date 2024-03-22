package com.example.myhome.features.chat.repositories

import com.example.myhome.features.chat.dtos.ChatAddRequest
import com.example.myhome.features.chat.ChatApiService
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.ChatListRequest
import com.example.myhome.features.chat.dtos.ChatUserListItemResponse
import com.example.myhome.features.chat.dtos.MessageListRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl(
    private val chatApiService: ChatApiService
): ChatRepository {
    override fun listMessage(chatId: Int) = flow {
        val request = MessageListRequest(chatId)
        emit(chatApiService.listMessage(request))
    }

    override fun listReceiver(): Flow<List<ChatUserListItemResponse>> = flow {
        val request = ChatListRequest(userId =  1)
        emit(chatApiService.listReceiver(request))
    }

    override fun addChat(users: ChatAddRequest): Flow<ChatListItemResponse> = flow {
        emit(chatApiService.addChat(users))
    }
}
