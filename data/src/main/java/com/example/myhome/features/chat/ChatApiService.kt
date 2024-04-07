package com.example.myhome.features.chat

import com.example.myhome.features.chat.dtos.ChatAddRequest
import com.example.myhome.features.chat.dtos.ChatAddResponse
import com.example.myhome.features.chat.dtos.ChatUserListResponse
import com.example.myhome.features.chat.dtos.MessageListRequest
import com.example.myhome.features.chat.dtos.MessageListResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("chat/get-receivers")
    suspend fun listReceiver(): ChatUserListResponse

    @POST("chat/get-messages")
    suspend fun listMessage(
        @Body request: MessageListRequest
    ): MessageListResponse

    @POST("chat/add-chat")
    suspend fun addChat(
        @Body request: ChatAddRequest
    ): ChatAddResponse

}