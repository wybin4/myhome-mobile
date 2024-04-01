package com.example.myhome.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhome.presentation.state.add.AddState
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.asAddState

abstract class BaseDigitPickerViewModel: ViewModel() {
    private val _addState = MutableLiveData<AddState>(AddState.None)
    val addState: LiveData<AddState> = _addState
    fun manageAddState(result: NetworkResult<Boolean>) {
        result.asAddState(_addState)
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
