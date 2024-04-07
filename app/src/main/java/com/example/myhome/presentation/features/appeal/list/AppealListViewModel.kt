package com.example.myhome.presentation.features.appeal.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myhome.features.appeal.models.AppealStatus
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.models.FilterListItemRequest
import com.example.myhome.presentation.features.appeal.AppealMapper
import com.example.myhome.presentation.features.appeal.AppealUiModel
import com.example.myhome.presentation.models.asPagingDataListStateWithFilter
import com.example.myhome.presentation.models.asPagingNetworkResult
import com.example.myhome.presentation.utils.filters.FilterObserveManager
import com.example.myhome.presentation.utils.filters.ListStateWithFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppealListViewModel @Inject constructor(
    private val appealRepository: AppealRepository,
    private val appealMapper: AppealMapper
) : ViewModel() {
    private val startAppealStatusList = listOf(
        AppealStatus.Processing, AppealStatus.Closed, AppealStatus.Rejected
    )
    val observeManager = FilterObserveManager(this::fetchAppealList, startAppealStatusList, "status")

    private val _appealListState = MutableLiveData<ListStateWithFilter>(ListStateWithFilter.Loading)
    val appealListState: LiveData<ListStateWithFilter> = _appealListState

    private val _appealList = MutableLiveData<PagingData<AppealUiModel>>()
    val appealList: LiveData<PagingData<AppealUiModel>> = _appealList

    init {
        observeManager.setup()
    }

    fun fetchAppealList(filters: List<FilterListItemRequest>? = null) {
        viewModelScope.launch {
            appealRepository.listAppeal(filters)
                .cachedIn(viewModelScope)
                .asPagingNetworkResult()
                .collectLatest {
                    it.asPagingDataListStateWithFilter { data ->
                        if (data != null) {
                            _appealList.value = data.map { d -> appealMapper.appealToUi(d) }
                        }
                    }
                }
            }
    }

    fun setState(state: ListStateWithFilter) {
        _appealListState.value = state
    }

}
