package com.example.myhome.presentation.features.chat.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.chat.ChatListItemResponse
import com.example.myhome.features.chat.repositories.ChatRepository
import com.example.myhome.presentation.features.chat.ChatMapper
import com.example.myhome.presentation.features.chat.models.ChatAddToGetParcelableModel
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asAddResourceWithData
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatAddViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val chatMapper: ChatMapper
): ViewModel() {
    private val _receiverList = MutableLiveData<List<ReceiverUiModel>>()
    val receiverList: LiveData<List<ReceiverUiModel>> = _receiverList

    private val _receiverListState = MutableLiveData<Resource>(Resource.Loading)
    val receiverListState: LiveData<Resource> = _receiverListState

    private val _chatAddState = MutableLiveData<AddResource>(AddResource.Loading)
    val chatAddState: LiveData<AddResource> = _chatAddState // OBSERVE

    private val _chatAddData = MutableLiveData<ChatListItemResponse>()
    val chatAddData: LiveData<ChatListItemResponse> = _chatAddData

    fun addToGetParcel(chat: ChatListItemResponse): ChatAddToGetParcelableModel {
        return chatMapper.chatAddToGetParcel(chat)
    }

    fun addChat(receiver: ReceiverUiModel) {
        viewModelScope.launch {
            chatRepository.addChat(chatMapper.addToRemote(receiver))
                .asNetworkResult()
                .collect {
                    it.asAddResourceWithData(_chatAddState) { data ->
                        _chatAddData.value = data
                    }
                }
        }
    }

    fun fetchChatList() {
        viewModelScope.launch {
            chatRepository.listReceiver()
                .asNetworkResult()
                .collect {
                    it.asListResource(_receiverListState) { data ->
                        _receiverList.value = chatMapper.receiverListToUi(data)
                    }
                }
        }
    }
}