package com.example.myhome.presentation.features.event.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.event.repositories.EventRepository
import com.example.myhome.presentation.features.event.EventMapper
import com.example.myhome.presentation.features.event.EventUiModel
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val eventMapper: EventMapper
) : ViewModel() {
    private val _eventList = MutableLiveData<List<EventUiModel>>()
    val eventList: LiveData<List<EventUiModel>> = _eventList

    private val _eventListState = MutableLiveData<Resource>(Resource.Loading)
    val eventListState: LiveData<Resource> = _eventListState

    fun fetchEventList() {
        viewModelScope.launch {
            eventRepository.listEvent()
                .asNetworkResult()
                .collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            val data = it.data
                            if (data.notifications.isNotEmpty() || data.votings.isNotEmpty()) {
                                _eventListState.value = Resource.Success
                                _eventList.value = eventMapper.eventListToUi(data)
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

    fun updateVoting(optionId: Int) {
        viewModelScope.launch {
            eventRepository.updateVoting(
                eventMapper.votingToRemote(optionId, userId = 1)
            ).collect{}
        }
    }
}
