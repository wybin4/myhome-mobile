package com.example.myhome.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.asAddResource

abstract class BaseDigitPickerViewModel: ViewModel() {
    private val _dataAddState = MutableLiveData<AddResource>(AddResource.None)
    val dataAddState: LiveData<AddResource> = _dataAddState
    fun manageAddState(result: NetworkResult<Boolean>) {
        result.asAddResource(_dataAddState)
    }
    abstract fun isCardLeftTextVisible(): Boolean
    abstract fun getCardLeftText(): String
    abstract fun isIcon(): Boolean
    abstract fun getIcon(): Int?
    abstract fun getIconText(): String
    abstract fun getTitle(): String
    abstract fun getSubtitle(): String
    abstract fun getRightTitle(): String
    abstract fun getLeftTitle(): String
    abstract fun getPrevValue(): Double
    abstract fun getRightText(): String
    abstract fun getUnit(): String
}
