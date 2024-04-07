package com.example.myhome.presentation.models

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.myhome.presentation.state.add.AddState
import com.example.myhome.presentation.state.get.GetState
import com.example.myhome.presentation.state.list.ListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val exception: Throwable) : NetworkResult<Nothing>
    data object Loading : NetworkResult<Nothing>
}

sealed interface PagingNetworkResult<out T> {
    data class Error(val exception: Throwable) : PagingNetworkResult<Nothing>
    data class Loading<T>(val data: T?) : PagingNetworkResult<T>
}

fun <T> Flow<T>.asNetworkResult(): Flow<NetworkResult<T>> = map<T, NetworkResult<T>> {
    NetworkResult.Success(
        it
    )
}
    .onStart { emit(NetworkResult.Loading) }
    .catch { emit(NetworkResult.Error(it)) }

fun <T> Flow<T>.asPagingNetworkResult(): Flow<PagingNetworkResult<T>> = map<T, PagingNetworkResult<T>> {
    PagingNetworkResult.Loading(
        it
    )
}
    .onStart { emit(PagingNetworkResult.Loading(null)) }
    .catch { emit(PagingNetworkResult.Error(it)) }


fun <T> NetworkResult<T>.asAddState(state: MutableLiveData<AddState>) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data is Boolean) {
                state.value = AddState.Success
            } else {
                state.value = AddState.CodeError
            }
        }
        is NetworkResult.Loading -> {
            state.value = AddState.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = AddState.NetworkError(errorMessage)
            }
        }
    }
}

fun <T> NetworkResult<T>.asAddStateWithData(
    state: MutableLiveData<AddState>,
    onSuccess: (data: T) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data != null) {
                state.value = AddState.Success
                onSuccess(data)
            } else {
                state.value = AddState.CodeError
            }
        }
        is NetworkResult.Loading -> {
            state.value = AddState.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = AddState.NetworkError(errorMessage)
            }
        }
    }
}

fun <T> NetworkResult<List<T>>.asListState(
    state: MutableLiveData<ListState>,
    onSuccess: (data: List<T>) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data.isNotEmpty()) {
                state.value = ListState.Success
                onSuccess(data)
            } else {
                state.value = ListState.Empty
            }
        }
        is NetworkResult.Loading -> {
            state.value = ListState.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = ListState.Error(errorMessage)
            }
        }
    }
}

fun <T> NetworkResult<List<T>>.asGetState(
    state: MutableLiveData<GetState>,
    onSuccess: (data: List<T>) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            state.value = GetState.Success
            onSuccess(data)
        }
        is NetworkResult.Loading -> {
            state.value = GetState.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = GetState.Error(errorMessage)
            }
        }
    }
}

fun <T : Any> PagingNetworkResult<PagingData<T>>.asPagingDataListState(
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

fun <T : Any> PagingNetworkResult<PagingData<T>>.asPagingDataListStateWithFilter(
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
