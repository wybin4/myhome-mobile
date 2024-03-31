package com.example.myhome.presentation.features.servicenotification

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myhome.features.CommonSocketService
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import com.example.myhome.features.servicenotifications.repositories.ServiceNotificationRepository
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.models.asPagingDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServiceNotificationListViewModel @Inject constructor(
    private val notificationRepository: ServiceNotificationRepository,
    private val converter: ServiceNotificationUiConverter
): ViewModel() {
    private val _notificationListState = MutableLiveData<Resource>(Resource.Loading)
    val notificationListState: LiveData<Resource> = _notificationListState

    private val _notificationList = MutableLiveData<PagingData<ServiceNotificationUiModel>>()
    val notificationList: LiveData<PagingData<ServiceNotificationUiModel>> = _notificationList

    private val localBinder: MutableLiveData<CommonSocketService.LocalBinder?> = MutableLiveData()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            val binder: CommonSocketService.LocalBinder = iBinder as CommonSocketService.LocalBinder
            localBinder.postValue(binder)
            binder.getService().apply {
                newNotification.observeForever {
                    viewModelScope.launch {
                        withContext(NonCancellable) {
                            notificationRepository.addServiceNotification().processNotificationList()
                        }
                    }
                }
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            localBinder.postValue(null)
        }
    }

    fun getServiceConnection(): ServiceConnection {
        return serviceConnection
    }

    fun fetchNotificationList() {
        viewModelScope.launch {
            notificationRepository.listServiceNotification().processNotificationList()
        }
    }

    private suspend fun Flow<PagingData<ServiceNotificationListItemResponse>>.processNotificationList() {
        this.cachedIn(viewModelScope)
            .asNetworkResult()
            .collect {
                it.asPagingDataResource(_notificationListState) { data ->
                    _notificationList.value = data.map { d -> converter.notificationToUi(d) }
                }
            }
    }

    fun readNotifications() {
        viewModelScope.launch {
            notificationRepository.readNotificationList()
        }
    }

    fun setState(resource: Resource) {
        _notificationListState.value = resource
    }
}