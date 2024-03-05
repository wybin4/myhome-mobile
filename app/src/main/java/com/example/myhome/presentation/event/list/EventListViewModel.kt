package com.example.myhome.presentation.event.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.event.usecases.EventListUseCase
import com.example.myhome.utils.mappers.EventUiMapper
import com.example.myhome.utils.models.EventUiModel
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventListUseCase: EventListUseCase,
    private val eventUiMapper: EventUiMapper
) : ViewModel() {
    private val _eventList = MutableLiveData<List<EventUiModel>>()
    val eventList: LiveData<List<EventUiModel>> = _eventList

    private val _eventListState = MutableLiveData<Resource>(Resource.Loading)
    val eventListState: LiveData<Resource> = _eventListState

    init {
        fetchEventList()
    }

    fun fetchEventList() {
        viewModelScope.launch {
            eventListUseCase()
                .asNetworkResult()
                .collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            val data = it.data
                            if (data.notifications.totalCount > 0 || data.votings.totalCount > 0) {
                                _eventListState.value = Resource.Success
                                _eventList.value = eventUiMapper.mapListToUi(data)
                            } else {
                                _eventListState.value = Resource.Empty
                            }
                        }
                        is NetworkResult.Loading -> {
                            _eventListState.value = Resource.Loading
                        }
                        is NetworkResult.Error -> {
                            val errorMessage = it.exception.message
                            if (errorMessage != null) {
                                _eventListState.value = Resource.Error(errorMessage)
                            }
                        }
                    }
                }
        }
    }
}
