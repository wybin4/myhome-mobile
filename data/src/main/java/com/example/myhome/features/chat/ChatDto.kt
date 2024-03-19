package com.example.myhome.features.chat

import com.example.myhome.models.DateConverter
import com.example.myhome.models.DateTimeConverter
import com.example.myhome.models.UserRole
import java.util.Date

data class ChatListRequest(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner
)

data class ChatListItemResponse(
    val id: Int,
    val createdAt: String,
    val lastMessage: MessageListItemResponse?,
    val countUnread: Int,
    val receiverName: String,
    val messages: List<MessageListItemResponse>
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}

data class ChatAddRequest(
    val users: List<ChatAddRequestItem>
)

data class ChatAddRequestItem(
    val userId: Int,
    val userRole: UserRole
)

data class ChatUserListItemResponse(
    val userId: Int,
    val userRole: UserRole,
    val name: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChatUserListItemResponse) return false

        if (userId != other.userId) return false
        if (userRole != other.userRole) return false

        return true
    }
}

data class MessageListItemResponse(
    val id: Int,
    val sender: ChatUserListItemResponse,
    val text: String,
    val createdAt: String,
    val readAt: String?,
    val status: MessageStatus
): DateConverter, DateTimeConverter {
    fun formatCreatedAt(): Date {
        return parseDateTime(createdAt)
    }

    fun formatReadAt(): Date {
        return parseDate(createdAt)
    }
}