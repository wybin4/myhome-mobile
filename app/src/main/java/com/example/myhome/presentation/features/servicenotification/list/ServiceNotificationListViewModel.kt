package com.example.myhome.presentation.features.servicenotification.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.servicenotification.ServiceNotificationListUseCase
import com.example.myhome.presentation.features.servicenotification.ServiceNotificationUiConverter
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceNotificationListViewModel @Inject constructor(
    private val notificationListUseCase: ServiceNotificationListUseCase,
    private val notificationUiConverter: ServiceNotificationUiConverter
) : ViewModel() {
    private val _notificationList = MutableLiveData<List<ServiceNotificationUiModel>>()
    val notificationList: LiveData<List<ServiceNotificationUiModel>> = _notificationList

    private val _listState = MutableLiveData<Resource>(Resource.Loading)
    val listState: LiveData<Resource> = _listState

    fun fetchNotificationList() {
        viewModelScope.launch {
            notificationListUseCase()
                .asNetworkResult()
                .collect {
                    it.asListResource(_listState) { data ->
                        _notificationList.value = notificationUiConverter.notificationListToUi(data)
                    }
                }
        }
    }
}