package com.example.myhome.presentation.features.servicenotification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myhome.features.servicenotifications.repositories.ServiceNotificationRepository
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.models.asPagingDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun fetchNotificationList() {
        viewModelScope.launch {
            notificationRepository.listServiceNotification()
                .cachedIn(viewModelScope)
                .asNetworkResult()
                .collect {
                    it.asPagingDataResource(_notificationListState) { data ->
                        _notificationList.value = data.map { d -> converter.notificationToUi(d) }
                    }
                }
        }
    }

    fun readNotifications() {
        viewModelScope.launch {
            notificationRepository.readNotificationList()
        }
    }
}