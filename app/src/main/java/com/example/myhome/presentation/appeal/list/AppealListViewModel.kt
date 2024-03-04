package com.example.myhome.presentation.appeal.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.usecases.AppealListUseCase
import com.example.myhome.utils.mappers.AppealUiMapper
import com.example.myhome.utils.models.AppealUiModel
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asListResource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppealListViewModel @Inject constructor(
    private val appealListUseCase: AppealListUseCase,
    private val appealUiMapper: AppealUiMapper
) : ViewModel() {
    private val _appealList = MutableLiveData<List<AppealUiModel>>()
    val appealList: LiveData<List<AppealUiModel>> = _appealList

    private val _appealListState = MutableLiveData<Resource>(Resource.Loading)
    val appealListState: LiveData<Resource> = _appealListState

    init {
        fetchAppealList()
    }

    fun fetchAppealList() {
        viewModelScope.launch {
            appealListUseCase()
                .asNetworkResult()
                .collect {
                    it.asListResource(_appealListState) { data ->
                        _appealList.value = appealUiMapper.appealListToUi(data)
                    }
                }
        }
    }
}
