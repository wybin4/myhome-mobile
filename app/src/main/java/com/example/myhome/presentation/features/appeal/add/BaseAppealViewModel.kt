package com.example.myhome.presentation.features.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.presentation.features.appeal.AppealUiPicker
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.asAddResource

abstract class BaseAppealViewModel(
    private val appealType: AppealType
) : ViewModel(), AppealUiPicker {
    private val _dataAddState = MutableLiveData<AddResource>(AddResource.None)
    val dataAddState: LiveData<AddResource> = _dataAddState

    fun getTypeString(): String {
        return getType(appealType)
    }

    fun manageAddState(result: NetworkResult<Boolean>) {
        result.asAddResource(_dataAddState)
    }

    fun codeError() {
        _dataAddState.value = AddResource.CodeError
    }
}
