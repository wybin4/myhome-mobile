package com.example.myhome.presentation.features.appeal.add.verify

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.presentation.features.appeal.add.BaseAppealViewModel
import com.example.myhome.presentation.features.common.CommonUiConverter
import com.example.myhome.presentation.features.common.models.SubscriberUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
open class BaseAppealVerifyViewModel @Inject constructor(
    private val subscriberRepository: SubscriberRepository,
    private val commonUiConverter: CommonUiConverter
) : BaseAppealViewModel(AppealType.VerifyIndividualMeter) {
    private val _subscriberList = MutableLiveData<List<SubscriberUiModel>>()
    protected val subscriberList: LiveData<List<SubscriberUiModel>> = _subscriberList

    protected val mutableDataState = MutableLiveData<Resource>(Resource.Loading)
    val dataState: LiveData<Resource> = mutableDataState

    var selectIssuedAt: Date? = null
    var selectVerifiedAt: Date? = null
    var selectAttachment: Bitmap? = null

    protected suspend fun fetchSubscriberList() {
        subscriberRepository.listSubscriber()
            .asNetworkResult()
            .collect {
                it.asListResource(mutableDataState) { data ->
                    _subscriberList.value = commonUiConverter.subscriberListToUi(data)
                }
        }
    }

}
