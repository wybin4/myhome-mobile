package com.example.myhome.features.chat.dtos

import com.example.myhome.models.DateTimeConverter
import com.example.myhome.models.UserRole
import java.util.Date

data class ChatListItemResponse(
    val id: String,
    val createdAt: String,
    val lastMessage: MessageCreatedAtListItemResponse?,
    var countUnread: Int,
    var receiverName: String
): DateTimeConverter {
    fun formatCreatedAt(): Date {
        return parseDateTime(createdAt)
    }
}

data class ChatAddResponse(
    val chat: ChatListItemResponse
)

data class ChatAddRequest(
    val users: List<ChatAddRequestItem>
)

data class ChatAddRequestItem(
    val userId: Int,
    val userRole: UserRole
)

data class ChatUserListResponse(
    val receivers: List<ChatUserListItemResponse>
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