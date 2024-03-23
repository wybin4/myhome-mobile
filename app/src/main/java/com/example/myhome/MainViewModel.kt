package com.example.myhome

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.features.CommonSocketService
import com.example.myhome.features.servicenotification.models.NotificationStatus
import com.example.myhome.presentation.features.chat.ChatMapper
import com.example.myhome.presentation.features.chat.MessageFormatter
import com.example.myhome.presentation.features.chat.models.ChatUiModel
import com.example.myhome.presentation.features.servicenotification.ServiceNotificationUiConverter
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.state.list.ListStateWithUnread
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notificationUiConverter: ServiceNotificationUiConverter,
    private val chatMapper: ChatMapper
) : ViewModel(), MessageFormatter {
    private val _notificationListState = MutableLiveData<ListStateWithUnread>(ListStateWithUnread.Loading)
    val notificationListState: LiveData<ListStateWithUnread> = _notificationListState

    val unreadMessagesCount = ObservableInt(0)

    private val _notificationList = MutableLiveData<List<ServiceNotificationUiModel>>()
    val notificationList: LiveData<List<ServiceNotificationUiModel>> = _notificationList

    private val _chatListState = MutableLiveData<ListStateWithUnread>(ListStateWithUnread.Loading)
    val chatListState: LiveData<ListStateWithUnread> = _chatListState

    private val _chatList = MutableLiveData<List<ChatUiModel>>()
    val chatList: LiveData<List<ChatUiModel>> = _chatList

    private val localBinder: MutableLiveData<CommonSocketService.LocalBinder?> = MutableLiveData<CommonSocketService.LocalBinder?>()

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

    fun readNotifications() {
        val socketService = localBinder.value?.getService()
        socketService?.readSocketNotifications()
    }

    private fun setupNotifications(service: CommonSocketService) {
        service.notificationList.observeForever { notifications ->
            if (notifications.isNotEmpty()) {
                val countUnread = notifications.count { it.status == NotificationStatus.Unread }
                _notificationListState.value = ListStateWithUnread.Success(countUnread)
                _notificationList.value = notificationUiConverter.notificationListToUi(notifications)
            } else {
                _notificationListState.value = ListStateWithUnread.Empty
            }
        }
        service.socketError.observeForever { message ->
            _notificationListState.value = ListStateWithUnread.Error(message)
        }
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
            val messageUi = chatMapper.messageToUi(message)
            if (!messageUi.isItMe) {
                var unreadCount = unreadMessagesCount.get()
                unreadMessagesCount.set(unreadCount++)
            }
        }
        service.socketError.observeForever { message ->
            _chatListState.value = ListStateWithUnread.Error(message)
        }
    }

}