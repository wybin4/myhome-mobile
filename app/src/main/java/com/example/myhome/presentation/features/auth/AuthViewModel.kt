package com.example.myhome.presentation.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.auth.dtos.LoginRequest
import com.example.myhome.features.auth.repositories.AuthRepository
import com.example.myhome.presentation.models.asAddState
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.state.add.AddState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    var selectEmail: String = ""
    var selectPassword: String = ""

    private val _addState = MutableLiveData<AddState>(AddState.None)
    val addState: LiveData<AddState> = _addState

    fun login() {
        viewModelScope.launch {
            authRepository.login(
                LoginRequest(
                    selectEmail, selectPassword
                )
            ).asNetworkResult().collect { it.asAddState(_addState) }
        }
    }
}