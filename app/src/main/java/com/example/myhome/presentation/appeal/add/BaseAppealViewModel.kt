package com.example.myhome.presentation.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.utils.converters.AppealUiConverter
import com.example.myhome.utils.models.AddResource
import com.example.myhome.utils.models.NetworkResult

abstract class BaseAppealViewModel(
    private val appealType: AppealType
) : ViewModel(), AppealUiConverter {
    private val _dataAddState = MutableLiveData<AddResource>(AddResource.None)
    val dataAddState: LiveData<AddResource> = _dataAddState

    fun getTypeString(): String {
        return getType(appealType)
    }

    fun manageState(result: NetworkResult<Boolean>) {
        when (result) {
            is NetworkResult.Success -> {
                val data = result.data
                if (data) {
                    _dataAddState.value = AddResource.Success
                } else {
                    _dataAddState.value = AddResource.CodeError
                }
            }
            is NetworkResult.Loading -> {
                _dataAddState.value = AddResource.Loading
            }
            is NetworkResult.Error -> {
                val errorMessage = result.exception.message
                if (errorMessage != null) {
                    _dataAddState.value = AddResource.NetworkError(errorMessage)
                }
            }
        }
    }

    fun codeError() {
        _dataAddState.value = AddResource.CodeError
    }
}
