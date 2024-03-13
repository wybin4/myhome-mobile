package com.example.myhome.presentation.features.appeal.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.appeal.usecases.AppealListUseCase
import com.example.myhome.presentation.features.appeal.AppealUiConverter
import com.example.myhome.presentation.features.appeal.AppealUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppealListViewModel @Inject constructor(
    private val appealListUseCase: AppealListUseCase,
    private val appealUiConverter: AppealUiConverter
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
                        _appealList.value = appealUiConverter.appealListToUi(data)
                    }
                }
        }
    }
}
