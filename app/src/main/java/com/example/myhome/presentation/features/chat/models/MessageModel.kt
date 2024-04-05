package com.example.myhome.presentation.features.chat.models

import android.view.View
import com.example.myhome.presentation.features.servicenotification.TimeConverter
import com.example.myhome.presentation.models.AdaptiveString
import com.example.myhome.presentation.utils.converters.CombinedDateConverter
import java.util.Date

data class MessageUiModel(
    val createdAt: Date,
    val messages: List<MessageCreatedAtUiModel>
): CombinedDateConverter {
    fun formatCreatedAt(): String {
        return formatDateHuman(createdAt)
    }
}

data class MessageCreatedAtUiModel(
    override val id: String,
    val isItMe: Boolean,
    val text: String,
    val createdAt: Long,
    val messageState: MessageState
) : AdaptiveString, TimeConverter {
    fun formatCreatedAt(): String {
        return formatHHMM(Date(createdAt))
    }
}

sealed class MessageState  {
    data class Success(val isItRead: Boolean) : MessageState()
    data object Loading : MessageState()
    data object Error : MessageState()

    val loadingVisibility: Int
        get() = if (this is Loading) View.VISIBLE else View.GONE

    val errorVisibility: Int
        get() = if (this is Error) View.VISIBLE else View.GONE

    val successReadVisibility: Int
        get() = if (this is Success && this.isItRead) View.VISIBLE else View.GONE

    val successUnreadVisibility: Int
        get() = if (this is Success && !this.isItRead) View.VISIBLE else View.GONE

}