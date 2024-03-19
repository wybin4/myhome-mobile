package com.example.myhome.features.chat.repositories

import com.example.myhome.features.chat.ChatAddRequest
import com.example.myhome.features.chat.ChatApiService
import com.example.myhome.features.chat.ChatListItemResponse
import com.example.myhome.features.chat.ChatListRequest
import com.example.myhome.features.chat.ChatUserListItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChatRepositoryImpl(
    private val chatApiService: ChatApiService
): ChatRepository {
    override fun listReceiver(): Flow<List<ChatUserListItemResponse>> = flow {
        val request = ChatListRequest(userId =  1)
        emit(chatApiService.listReceiver(request))
    }

    override fun addChat(users: ChatAddRequest): Flow<ChatListItemResponse> = flow {
        emit(chatApiService.addChat(users))
    }
}
