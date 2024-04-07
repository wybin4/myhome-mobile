package com.example.myhome.presentation.features.charge.models.networkresults

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.PagingNetworkResult

fun <T> NetworkResult<List<T>>.asChargeListResource(
    state: MutableLiveData<ChargeListResource>,
    onSuccess: (data: List<T>) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data.isNotEmpty()) {
                state.value = ChargeListResource.Success
                onSuccess(data)
            } else {
                state.value = ChargeListResource.Empty
            }
        }
        is NetworkResult.Loading -> {
            state.value = ChargeListResource.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = ChargeListResource.NetworkError(errorMessage)
            }
        }
    }
}

fun <T : Any> PagingNetworkResult<PagingData<T>>.asChargePagingDataResource(
    onSuccess: (data: PagingData<T>?) -> Unit
) {
    when (this) {
        is PagingNetworkResult.Loading -> {
            val data = this.data
            onSuccess(data)
        }
        else -> {}
    }
}