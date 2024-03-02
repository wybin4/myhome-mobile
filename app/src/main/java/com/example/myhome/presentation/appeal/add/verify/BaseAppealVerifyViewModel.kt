package com.example.myhome.presentation.appeal.add.verify

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.appeal.add.BaseAppealViewModel
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
open class BaseAppealVerifyViewModel @Inject constructor(
    val subscriberListUseCase: SubscriberListUseCase,
) : BaseAppealViewModel(AppealType.VerifyIndividualMeter) {
    private val _subscriberList = MutableLiveData<List<SubscriberGetModel>>()
    protected val subscriberList: LiveData<List<SubscriberGetModel>> = _subscriberList

    private val _dataState = MutableLiveData<Resource>(Resource.Loading)
    val dataState: LiveData<Resource> = _dataState

    var selectIssuedAt: Date? = null
    var selectVerifiedAt: Date? = null
    var selectAttachment: Bitmap? = null

    fun setDataGetState(resource: Resource) {
        _dataState.value = resource
    }

    protected suspend fun fetchSubscriberList() {
        subscriberListUseCase()
            .asNetworkResult()
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val data = result.data
                        if (data.isNotEmpty()) {
                            _dataState.value = Resource.Success
                            _subscriberList.value = data
                        } else {
                            _dataState.value = Resource.Empty
                        }
                    }
                    is NetworkResult.Loading -> {
                        _dataState.value = Resource.Loading
                    }
                    is NetworkResult.Error -> {
                        val errorMessage = result.exception.message
                        if (errorMessage != null) {
                            _dataState.value = Resource.Error(errorMessage)
                        }
                    }
                }
        }
    }

}
