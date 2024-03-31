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
import com.example.myhome.presentation.features.chat.ChatMapper
import com.example.myhome.presentation.features.chat.models.ChatAddToGetParcelableModel
import com.example.myhome.presentation.features.chat.models.MessageState
import com.example.myhome.presentation.features.chat.models.MessageUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatGetViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val chatMapper: ChatMapper
): ViewModel() {
    var isItView = false

    private var loadingIdCounter = 0

    lateinit var chatParcelable: ChatAddToGetParcelableModel

    private val _messageList = MutableLiveData<List<MessageUiModel>>()
    val messageList: LiveData<List<MessageUiModel>> = _messageList

    private val _messageListState = MutableLiveData<Resource>(Resource.Loading)
    val messageListState: LiveData<Resource> = _messageListState

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
                    val index = currentList.indexOfFirst { it.createdAt == message.createdAt }
                    if (index != -1) {
                        val updatedList = currentList.toMutableList()
                        updatedList[index] = updatedList[index].copy(
                            id = messageId,
                            messageState = MessageState.Success(message.status == MessageStatus.Read)
                        )
                        _messageList.postValue(updatedList)
                    } else {
                        updateMessageStatesInList(MessageState.Error)
                    }
                } else {
                    val currentList = messageList.value.orEmpty()
                    _messageList.postValue(currentList + newMessageUi)
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
                    val updatedMessage = messages.find { it.id == message.id }
                    if (updatedMessage != null && updatedMessage.status == MessageStatus.Read) {
                        message.copy(messageState = MessageState.Success(true))
                    } else {
                        message
                    }
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
            if (message.messageState == MessageState.Loading) {
                message.copy(messageState = state)
            } else {
                message
            }
        }
        _messageList.postValue(updatedList)
    }

    fun fetchMessageList() {
        viewModelScope.launch {
            chatRepository.listMessage(chatParcelable.id)
                .asNetworkResult()
                .collect {
                    it.asListResource(_messageListState) { data ->
                        _messageList.value = chatMapper.messageListToUi(data)
                    }
                }
        }
    }

    fun sendMessage(text: String) {
        loadingIdCounter++
        val createdAt = Date().time
        val currentList = messageList.value.orEmpty()
        val loadingMessage = MessageUiModel(
            id = loadingIdCounter,
            isItMe = true,
            text = text,
            createdAt = createdAt,
            messageState = MessageState.Loading
        )
        val newList = currentList + loadingMessage
        _messageList.value = newList
        if (_messageListState.value == Resource.Empty) {
            _messageListState.value = Resource.Success
        }
        val socketService = localBinder.value?.getService()
        socketService?.sendSocketMessage(chatMapper.messageAddToRemote(chatParcelable.id, text, createdAt))
    }
}