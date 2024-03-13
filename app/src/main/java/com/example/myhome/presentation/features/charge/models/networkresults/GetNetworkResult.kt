package com.example.myhome.presentation.features.charge.models.networkresults

import androidx.lifecycle.MutableLiveData
import com.example.myhome.presentation.features.charge.models.resources.ChargeGetResource
import com.example.myhome.presentation.models.NetworkResult

fun <T> NetworkResult<T>.asChargeGetResource(
    state: MutableLiveData<ChargeGetResource>,
    onSuccess: (data: T) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data != null) {
                state.value = ChargeGetResource.Success
                onSuccess(data)
            } else {
                state.value = ChargeGetResource.CodeError
            }
        }
        is NetworkResult.Loading -> {
            state.value = ChargeGetResource.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = ChargeGetResource.NetworkError(errorMessage)
            }
        }
    }
}