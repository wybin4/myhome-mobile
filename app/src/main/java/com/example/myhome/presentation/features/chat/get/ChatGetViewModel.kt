package com.example.myhome.presentation.features.chat.get

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.CommonSocketService
import com.example.myhome.features.chat.MessageStatus
import com.example.myhome.features.chat.repositories.ChatRepository
import com.example.myhome.models.DateConverter
import com.example.myhome.presentation.features.chat.ChatMapper
import com.example.myhome.presentation.features.chat.models.ChatAddToGetParcelableModel
import com.example.myhome.presentation.features.chat.models.MessageCreatedAtUiModel
import com.example.myhome.presentation.features.chat.models.MessageState
import com.example.myhome.presentation.features.chat.models.MessageUiModel
import com.example.myhome.presentation.models.asListState
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.state.list.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatGetViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val chatMapper: ChatMapper
): ViewModel(), DateConverter {
    var isItView = false

    private var loadingIdCounter = 0

    lateinit var chatParcelable: ChatAddToGetParcelableModel

    private val _messageList = MutableLiveData<List<MessageUiModel>>()
    val messageList: LiveData<List<MessageUiModel>> = _messageList

    private val _messageListState = MutableLiveData<ListState>(ListState.Loading)
    val messageListState: LiveData<ListState> = _messageListState

    private val localBinder: MutableLiveData<CommonSocketService.LocalBinder?> = MutableLiveData()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder: CommonSocketService.LocalBinder = iBinder as CommonSocketService.LocalBinder
            localBinder.postValue(binder)
            setupMessages(binder.getService())
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            localBinder.postValue(null)
        }
    }

    fun getServiceConnection(): ServiceConnection {
        return serviceConnection
    }

    private fun setupMessages(service: CommonSocketService) {
        service.newMessage.observeForever { message ->
            if (message != null) {
                val messageId = message.id
                val newMessageUi = chatMapper.messageAddToUi(message)
                if (newMessageUi.isItMe) {
                    val currentList = messageList.value.orEmpty()
                    val existingItem = currentList.find { it.messages.any { m -> m.createdAt == message.createdAt } }
                    if (existingItem != null) {
                        val updatedMessages = existingItem.messages.map { innerMessage ->
                            if (innerMessage.createdAt == message.createdAt) {
                                innerMessage.copy(
                                    id = messageId,
                                    messageState = MessageState.Success(message.status == MessageStatus.Read)
                                )
                            } else {
                                innerMessage
                            }
                        }
                        val updatedItem = existingItem.copy(messages = updatedMessages)
                        val updatedList = currentList.map { if (it == existingItem) updatedItem else it }
                        _messageList.value = updatedList
                    } else {
                        updateMessageStatesInList(MessageState.Error)
                    }
                } else {
                    val ddMMMMYYYYDate = getDDMMMMYYYYDate(newMessageUi.createdAt)
                    _messageList.value = updateMessageListWithLoadingMessage(ddMMMMYYYYDate, newMessageUi)
                    if (isItView) {
                        service.readSocketMessage(chatMapper.messageReadToRemote(chatParcelable.id, messageId))
                    }
                }
            } else {
                updateMessageStatesInList(MessageState.Error)
            }
        }
        service.readMessages.observeForever { messages ->
            if (messages.isNotEmpty()) {
                val updatedMessageList = _messageList.value?.map { message ->
                    val updatedMessages = message.messages.map { innerMessage ->
                        val updatedMessage = messages.find { it.id == innerMessage.id }
                        if (updatedMessage != null && updatedMessage.status == MessageStatus.Read) {
                            innerMessage.copy(messageState = MessageState.Success(true))
                        } else {
                            innerMessage
                        }
                    }
                    message.copy(messages = updatedMessages)
                }

                updatedMessageList?.let { _messageList.value = it }
            }
        }

        service.socketError.observeForever {
            updateMessageStatesInList(MessageState.Error)
        }
    }

    private fun updateMessageStatesInList(state: MessageState) {
        val updatedList = _messageList.value.orEmpty().map { message ->
            val updatedMessages = message.messages.map { innerMessage ->
                if (innerMessage.messageState == MessageState.Loading) {
                    innerMessage.copy(messageState = state)
                } else {
                    innerMessage
                }
            }
            message.copy(messages = updatedMessages)
        }
        _messageList.value = updatedList
    }

    fun fetchMessageList() {
        viewModelScope.launch {
            chatRepository.listMessage(chatParcelable.id)
                .asNetworkResult()
                .collect {
                    it.asListState(_messageListState) { data ->
                        _messageList.value = chatMapper.messageListToUi(data)
                    }
                }
        }
    }

    private fun getDDMMMMYYYYDate(dateLong: Long): Date {
        return parseDate(formatDate(Date(dateLong)))
    }

    private fun getDDMMMMYYYYDate(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    fun sendMessage(text: String) {
        loadingIdCounter++
        val createdAt = Date()
        val createdAtLong = createdAt.time

        val loadingMessage = MessageCreatedAtUiModel(
            id = loadingIdCounter.toString(),
            isItMe = true,
            text = text,
            createdAt = createdAtLong,
            messageState = MessageState.Loading
        )

        _messageList.value = updateMessageListWithLoadingMessage(createdAt, loadingMessage)

        if (_messageListState.value == ListState.Empty) {
            _messageListState.value = ListState.Success
        }
        val socketService = localBinder.value?.getService()
        socketService?.sendSocketMessage(chatMapper.messageAddToRemote(chatParcelable.id, text, createdAtLong))
    }

    private fun updateMessageListWithLoadingMessage(createdAt: Date, loadingMessage: MessageCreatedAtUiModel): List<MessageUiModel> {
        val existingList = _messageList.value ?: emptyList()
        val ddMMMMYYYYDate = getDDMMMMYYYYDate(createdAt)
        val existingItemIndex = existingList.indexOfFirst { it.createdAt == ddMMMMYYYYDate }

        return if (existingItemIndex != -1) {
            val existingItem = existingList[existingItemIndex]
            val updatedMessages = existingItem.messages + loadingMessage
            val updatedItem = existingItem.copy(messages = updatedMessages)
            existingList.toMutableList().apply { this[existingItemIndex] = updatedItem }
        } else {
            val newItem = MessageUiModel(
                createdAt = ddMMMMYYYYDate,
                messages = listOf(loadingMessage)
            )
            existingList + newItem
        }
    }

}