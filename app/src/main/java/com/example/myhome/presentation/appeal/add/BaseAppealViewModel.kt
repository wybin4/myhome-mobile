package com.example.myhome.presentation.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.utils.converters.AppealUiConverter
import com.example.myhome.utils.models.Resource
import java.util.Date

abstract class BaseAppealViewModel(
    private val appealType: AppealType
) : ViewModel(), AppealUiConverter {

    protected val _appealState = MutableLiveData<Resource>(Resource.Loading)
    val appealState: LiveData<Resource> = _appealState

    fun getTypeString(): String {
        return getType(appealType)
    }
}
