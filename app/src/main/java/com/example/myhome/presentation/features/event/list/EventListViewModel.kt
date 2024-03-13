package com.example.myhome.presentation.features.event.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.event.models.VotingUpdateModel
import com.example.myhome.features.event.usecases.EventListUseCase
import com.example.myhome.features.event.usecases.VotingUpdateUseCase
import com.example.myhome.presentation.features.event.EventUiConverter
import com.example.myhome.presentation.features.event.EventUiModel
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventListUseCase: EventListUseCase,
    private val votingUpdateUseCase: VotingUpdateUseCase,
    private val eventUiConverter: EventUiConverter
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
                                _eventList.value = eventUiConverter.eventListToUi(data)
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
            votingUpdateUseCase(
                VotingUpdateModel(
                    optionId = optionId,
                    userId = 1
                )
            ).collect{}
        }
    }
}
