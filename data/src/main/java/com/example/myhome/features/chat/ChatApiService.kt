package com.example.myhome.features.chat

import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("chat/get-receivers")
    suspend fun listReceiver(
        @Body request: ChatListRequest
    ): List<ChatUserListItemResponse>

    @POST("chat/add-chat")
    suspend fun addChat(
        @Body request: ChatAddRequest
    ): ChatListItemResponse

}