package com.example.myhome

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.features.SocketService
import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import com.example.myhome.features.servicenotification.models.NotificationStatus
import com.example.myhome.presentation.features.servicenotification.ServiceNotificationUiConverter
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.state.list.NotificationListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notificationUiConverter: ServiceNotificationUiConverter
) : ViewModel() {
    var socketService: SocketService? = null

    private val _listState = MutableLiveData<NotificationListState>(NotificationListState.Loading)
    val listState: LiveData<NotificationListState> = _listState

    private val _newNotification = MutableLiveData<ServiceNotificationListItemResponse>()
    val newNotification: LiveData<ServiceNotificationListItemResponse> = _newNotification

    private val _notificationList = MutableLiveData<List<ServiceNotificationUiModel>>()
    val notificationList: LiveData<List<ServiceNotificationUiModel>> = _notificationList

    private val localBinder: MutableLiveData<SocketService.LocalBinder?> = MutableLiveData<SocketService.LocalBinder?>()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder: SocketService.LocalBinder = iBinder as SocketService.LocalBinder
            localBinder.postValue(binder)
            socketService = binder.getService()
            socketService!!.notificationList.observeForever { notifications ->
                if (notifications.isNotEmpty()) {
                    val hasUnread = notifications.count { it.status == NotificationStatus.Unread } > 0
                    _listState.value = NotificationListState.Success(hasUnread)
                } else {
                    _listState.value = NotificationListState.Empty
                }
                _notificationList.value = notificationUiConverter.notificationListToUi(notifications)
            }
            socketService!!.socketError.observeForever { message ->
                _listState.value = NotificationListState.Error(message)
            }
            socketService!!.newNotification.observeForever {
                _newNotification.value = it
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            localBinder.postValue(null)
        }
    }

    fun getServiceConnection(): ServiceConnection {
        return serviceConnection
    }

    fun getBinder(): LiveData<SocketService.LocalBinder?> {
        return localBinder
    }

}