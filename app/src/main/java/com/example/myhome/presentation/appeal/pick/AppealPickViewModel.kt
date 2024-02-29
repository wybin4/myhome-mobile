package com.example.myhome.presentation.appeal.pick

import androidx.lifecycle.ViewModel
import com.example.myhome.appeal.models.AppealType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppealPickViewModel @Inject constructor() : ViewModel() {
    var selected: AppealType? = null
}
