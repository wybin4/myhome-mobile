package com.example.myhome.presentation.features.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.presentation.features.appeal.AppealUiPicker
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.asAddState
import com.example.myhome.presentation.state.add.AddState

abstract class BaseAppealViewModel(
    private val appealType: AppealType
) : ViewModel(), AppealUiPicker {
    private val _addState = MutableLiveData<AddState>(AddState.None)
    val addState: LiveData<AddState> = _addState

    fun getTypeString(): String {
        return getType(appealType)
    }

    fun manageAddState(result: NetworkResult<Boolean>) {
        result.asAddState(_addState)
    }

    fun codeError() {
        _addState.value = AddState.CodeError
    }
}
