package com.example.myhome.presentation.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.utils.converters.AppealUiConverter
import com.example.myhome.utils.models.AddResource
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.asAddResource

abstract class BaseAppealViewModel(
    private val appealType: AppealType
) : ViewModel(), AppealUiConverter {
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
