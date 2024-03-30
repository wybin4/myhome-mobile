package com.example.myhome.presentation.features.appeal.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.features.event.models.EventTypeResponse
import com.example.myhome.presentation.features.appeal.AppealMapper
import com.example.myhome.presentation.features.appeal.AppealUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.models.asPagingDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AppealListViewModel @Inject constructor(
    private val appealRepository: AppealRepository,
    private val appealMapper: AppealMapper
) : ViewModel() {
    private val _appealListState = MutableLiveData<Resource>(Resource.Loading)
    val appealListState: LiveData<Resource> = _appealListState

    private val _appealList = MutableLiveData<PagingData<AppealUiModel>>()
    val appealList: LiveData<PagingData<AppealUiModel>> = _appealList

    var selectCreatedAt: Date? = null
    var selectEventType: MutableList<EventTypeResponse> = mutableListOf(
        EventTypeResponse.Notification, EventTypeResponse.Voting
    )

    fun fetchAppealList() {
        viewModelScope.launch {
            appealRepository.listAppeal()
                .cachedIn(viewModelScope)
                .asNetworkResult()
                .collectLatest {
                    it.asPagingDataResource(_appealListState) { data ->
                        _appealList.value = data.map { d -> appealMapper.appealToUi(d) }
                    }
                }
            }
    }

    fun setState(resource: Resource) {
        _appealListState.value = resource
    }

}
