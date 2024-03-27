package com.example.myhome.presentation.features.event.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myhome.features.event.repositories.EventRepository
import com.example.myhome.presentation.features.event.EventMapper
import com.example.myhome.presentation.features.event.EventUiModel
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.models.asPagingDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val eventMapper: EventMapper
) : ViewModel() {
    private val _eventList = MutableLiveData<PagingData<EventUiModel>>()
    val eventList: LiveData<PagingData<EventUiModel>> = _eventList

    private val _eventListState = MutableLiveData<Resource>(Resource.Loading)
    val eventListState: LiveData<Resource> = _eventListState

    fun fetchEventList() {
        viewModelScope.launch {
            eventRepository.listEvent()
                .cachedIn(viewModelScope)
                .asNetworkResult()
                .collectLatest {
                    it.asPagingDataResource(_eventListState) { data ->
                        _eventList.value = data.map { d -> eventMapper.eventToUi(d) }
                    }
                }
        }
    }

    fun setState(resource: Resource) {
        _eventListState.value = resource
    }


    fun updateVoting(optionId: Int) {
        viewModelScope.launch {
            eventRepository.updateVoting(
                eventMapper.votingToRemote(optionId, userId = 1)
            ).collect{}
        }
    }
}
