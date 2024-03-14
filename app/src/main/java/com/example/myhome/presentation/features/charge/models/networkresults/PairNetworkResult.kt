package com.example.myhome.presentation.features.charge.models.networkresults

import androidx.lifecycle.MutableLiveData
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource
import com.example.myhome.presentation.models.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T1, T2> NetworkResult<Pair<List<T1>, List<T2>>>.pairNetworkResultAsChargeListResource(
    state: MutableLiveData<ChargeListResource>,
    onSuccess: (data: Pair<List<T1>, List<T2>>) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data.first.isNotEmpty() && data.second.isNotEmpty()) {
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

fun <T1, T2> Flow<Pair<T1, T2>>.pairAsNetworkResult(): Flow<NetworkResult<Pair<T1, T2>>> = map<Pair<T1, T2>, NetworkResult<Pair<T1, T2>>> { pair ->
    NetworkResult.Success(
        pair
    )
}
    .onStart { emit(NetworkResult.Loading) }
    .catch { emit(NetworkResult.Error(it)) }