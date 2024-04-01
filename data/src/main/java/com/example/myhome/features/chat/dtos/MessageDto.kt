package com.example.myhome.features.chat.dtos

import com.example.myhome.features.chat.MessageStatus
import com.example.myhome.models.DateConverter
import com.example.myhome.models.UserRole
import java.util.Date

data class MessageListRequest(
    val chatId: Int,
    val userId: Int,
    val userRole: UserRole = UserRole.Owner
)

data class MessageListItemResponse(
    val createdAt: String,
    val messages: List<MessageCreatedAtListItemResponse>
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}

data class MessageCreatedAtListItemResponse(
    val id: Int,
    val sender: ChatUserListItemResponse,
    val text: String,
    val createdAt: Long,
    val readAt: String?,
    val status: MessageStatus
)

data class MessageAddRequest(
    val chatId: Int,
    val text: String,
    val senderId: Int,
    val senderRole: UserRole = UserRole.Owner,
    val createdAt: Long
)

data class MessageAddResponse(
    val id: Int,
    val sender: ChatUserListItemResponse,
    val text: String,
    val createdAt: Long,
    val readAt: String?,
    val status: MessageStatus,
    val chatId: Int
)

data class MessageReadRequest(
    val messageId: Int,
    val chatId: Int,
    val senderId: Int,
    val senderRole: UserRole = UserRole.Owner,
)