package com.example.myhome.presentation.features.appeal.add.verify

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.features.appeal.add.BaseAppealViewModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
open class BaseAppealVerifyViewModel @Inject constructor(
    val subscriberListUseCase: SubscriberListUseCase,
) : BaseAppealViewModel(AppealType.VerifyIndividualMeter) {
    private val _subscriberList = MutableLiveData<List<SubscriberListItemModel>>()
    protected val subscriberList: LiveData<List<SubscriberListItemModel>> = _subscriberList

    protected val mutableDataState = MutableLiveData<Resource>(Resource.Loading)
    val dataState: LiveData<Resource> = mutableDataState

    var selectIssuedAt: Date? = null
    var selectVerifiedAt: Date? = null
    var selectAttachment: Bitmap? = null

    protected suspend fun fetchSubscriberList() {
        subscriberListUseCase()
            .asNetworkResult()
            .collect {
                it.asListResource(mutableDataState) { data ->
                    _subscriberList.value = data
                }
        }
    }

}
