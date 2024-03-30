package com.example.myhome

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.features.CommonSocketService
import com.example.myhome.presentation.features.chat.ChatMapper
import com.example.myhome.presentation.features.chat.models.ChatUiModel
import com.example.myhome.presentation.state.list.ListStateWithUnread
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val chatMapper: ChatMapper
) : ViewModel() {
    val unreadMessagesCount = ObservableInt(0)
    val hasUnreadNotifications = ObservableInt(-1)

    private val _chatListState = MutableLiveData<ListStateWithUnread>(ListStateWithUnread.Loading)
    val chatListState: LiveData<ListStateWithUnread> = _chatListState

    private val _chatList = MutableLiveData<List<ChatUiModel>>()
    val chatList: LiveData<List<ChatUiModel>> = _chatList

    val localBinder: MutableLiveData<CommonSocketService.LocalBinder?> = MutableLiveData<CommonSocketService.LocalBinder?>()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder: CommonSocketService.LocalBinder = iBinder as CommonSocketService.LocalBinder
            localBinder.postValue(binder)
            binder.getService().let { service ->
                setupNotifications(service)
                setupChats(service)
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            localBinder.postValue(null)
        }
    }

    fun getServiceConnection(): ServiceConnection {
        return serviceConnection
    }

    private fun setupNotifications(service: CommonSocketService) {
        service.hasUnreadNotifications.addOnPropertyChangedCallback(
            object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    hasUnreadNotifications.set(service.hasUnreadNotifications.get())
                }
        })
    }

    private fun setupChats(service: CommonSocketService) {
        service.chatList.observeForever { chats ->
            if (chats.isNotEmpty()) {
                val uiChatList = chatMapper.chatListToUi(chats)
                _chatList.value = uiChatList
                val countUnread = uiChatList.sumOf { it.countUnread }
                unreadMessagesCount.set(countUnread)
                _chatListState.value = ListStateWithUnread.Success(countUnread)
            } else {
                _chatListState.value = ListStateWithUnread.Empty
            }
        }
        service.newMessage.observeForever { message ->
            val messageUi = chatMapper.messageAddToUi(message)
            if (!messageUi.isItMe) {
                unreadMessagesCount.set(unreadMessagesCount.get() + 1)
            }
        }
        service.socketError.observeForever { message ->
            _chatListState.value = ListStateWithUnread.Error(message)
        }
    }

}