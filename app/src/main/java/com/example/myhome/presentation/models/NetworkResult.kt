package com.example.myhome.presentation.models

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.myhome.presentation.utils.filters.ListStateWithFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val exception: Throwable) : NetworkResult<Nothing>
    data object Loading : NetworkResult<Nothing>
}

fun <T> Flow<T>.asNetworkResult(): Flow<NetworkResult<T>> = map<T, NetworkResult<T>> {
    NetworkResult.Success(
        it
    )
}
    .onStart { emit(NetworkResult.Loading) }
    .catch { emit(NetworkResult.Error(it)) }

fun <T> NetworkResult<T>.asAddResource(state: MutableLiveData<AddResource>) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data is Boolean) {
                state.value = AddResource.Success
            } else {
                state.value = AddResource.CodeError
            }
        }
        is NetworkResult.Loading -> {
            state.value = AddResource.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = AddResource.NetworkError(errorMessage)
            }
        }
    }
}

fun <T> NetworkResult<T>.asAddResourceWithData(
    state: MutableLiveData<AddResource>,
    onSuccess: (data: T) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data != null) {
                state.value = AddResource.Success
                onSuccess(data)
            } else {
                state.value = AddResource.CodeError
            }
        }
        is NetworkResult.Loading -> {
            state.value = AddResource.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = AddResource.NetworkError(errorMessage)
            }
        }
    }
}

fun <T> NetworkResult<List<T>>.asListResource(
    state: MutableLiveData<Resource>,
    onSuccess: (data: List<T>) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            if (data.isNotEmpty()) {
                state.value = Resource.Success
                onSuccess(data)
            } else {
                state.value = Resource.Empty
            }
        }
        is NetworkResult.Loading -> {
            state.value = Resource.Loading
        }
        is NetworkResult.Error -> {
            val errorMessage = this.exception.message
            if (errorMessage != null) {
                state.value = Resource.Error(errorMessage)
            }
        }
    }
}

fun <T : Any> NetworkResult<PagingData<T>>.asPagingDataResource(
    state: MutableLiveData<Resource>,
    onSuccess: (data: PagingData<T>) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            state.value = Resource.Success
            onSuccess(data)
        }
        is NetworkResult.Loading -> {
            state.value = Resource.Loading
        }
        else -> {}
    }
}

fun <T : Any> NetworkResult<PagingData<T>>.asPagingDataResourceWithFilter(
    state: MutableLiveData<ListStateWithFilter>,
    onSuccess: (data: PagingData<T>) -> Unit
) {
    when (this) {
        is NetworkResult.Success -> {
            val data = this.data
            state.value = ListStateWithFilter.Success
            onSuccess(data)
        }
        is NetworkResult.Loading -> {
            state.value = ListStateWithFilter.Loading
        }
        else -> {}
    }
}
