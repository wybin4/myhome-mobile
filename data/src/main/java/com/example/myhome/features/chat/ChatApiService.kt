package com.example.myhome.features.chat

import com.example.myhome.features.chat.dtos.ChatAddRequest
import com.example.myhome.features.chat.dtos.ChatListItemResponse
import com.example.myhome.features.chat.dtos.ChatListRequest
import com.example.myhome.features.chat.dtos.ChatUserListItemResponse
import com.example.myhome.features.chat.dtos.MessageListItemResponse
import com.example.myhome.features.chat.dtos.MessageListRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("chat/get-receivers")
    suspend fun listReceiver(
        @Body request: ChatListRequest
    ): List<ChatUserListItemResponse>

    @POST("chat/get-messages")
    suspend fun listMessage(
        @Body request: MessageListRequest
    ): List<MessageListItemResponse>

    @POST("chat/add-chat")
    suspend fun addChat(
        @Body request: ChatAddRequest
    ): ChatListItemResponse

}