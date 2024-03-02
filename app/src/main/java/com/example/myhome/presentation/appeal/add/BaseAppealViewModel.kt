package com.example.myhome.presentation.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.utils.converters.AppealUiConverter
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource

abstract class BaseAppealViewModel(
    private val appealType: AppealType
) : ViewModel(), AppealUiConverter {
    private val _appealState = MutableLiveData<Resource>(Resource.Loading)
    val appealState: LiveData<Resource> = _appealState

    fun getTypeString(): String {
        return getType(appealType)
    }

    fun manageState(result: NetworkResult<Boolean>) {
        when (result) {
            is NetworkResult.Success -> {
                val data = result.data
                if (data) {
                    _appealState.value = Resource.Success
                } else {
                    _appealState.value = Resource.Error("") // ????
                }
            }
            is NetworkResult.Loading -> {
                _appealState.value = Resource.Loading
            }
            is NetworkResult.Error -> {
                val errorMessage = result.exception.message
                if (errorMessage != null) {
                    _appealState.value = Resource.Error(errorMessage)
                }
            }
        }
    }
}
