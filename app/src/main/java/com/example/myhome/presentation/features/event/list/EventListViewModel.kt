package com.example.myhome.presentation.features.event.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myhome.features.event.models.EventTypeResponse
import com.example.myhome.features.event.repositories.EventRepository
import com.example.myhome.models.FilterListItemRequest
import com.example.myhome.presentation.features.event.EventMapper
import com.example.myhome.presentation.features.event.EventUiModel
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.models.asPagingDataListStateWithFilter
import com.example.myhome.presentation.utils.filters.FilterObserveManager
import com.example.myhome.presentation.utils.filters.ListStateWithFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val eventMapper: EventMapper
) : ViewModel() {
    private val startEventTypeList = listOf(
        EventTypeResponse.Voting, EventTypeResponse.Notification
    )
    val observeManager = FilterObserveManager(this::fetchEventList, startEventTypeList, "eventType")

    private val _eventList = MutableLiveData<PagingData<EventUiModel>>()
    val eventList: LiveData<PagingData<EventUiModel>> = _eventList

    private val _eventListState = MutableLiveData<ListStateWithFilter>(ListStateWithFilter.Loading)
    val eventListState: LiveData<ListStateWithFilter> = _eventListState

    init {
        observeManager.setup()
    }

    fun fetchEventList(filters: List<FilterListItemRequest>? = null) {
        viewModelScope.launch {
            eventRepository.listEvent(filters)
                .cachedIn(viewModelScope)
                .asNetworkResult()
                .collectLatest {
                    it.asPagingDataListStateWithFilter(_eventListState) { data ->
                        _eventList.value = data.map { d -> eventMapper.eventToUi(d) }
                    }
                }
        }
    }

    fun setState(state: ListStateWithFilter) {
        _eventListState.value = state
    }


    fun updateVoting(optionId: Int) {
        viewModelScope.launch {
            eventRepository.updateVoting(
                eventMapper.votingToRemote(optionId)
            ).collect{}
        }
    }
}
