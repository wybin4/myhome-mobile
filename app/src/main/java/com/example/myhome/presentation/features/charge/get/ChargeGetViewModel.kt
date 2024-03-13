package com.example.myhome.presentation.features.charge.get

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.charge.usecases.PaymentListUseCase
import com.example.myhome.features.charge.usecases.SinglePaymentDocumentGetUseCase
import com.example.myhome.presentation.features.charge.ChargeUiMapper
import com.example.myhome.presentation.features.charge.converters.ChargeUiConverter
import com.example.myhome.presentation.features.charge.models.ChargeListToGetParcelableModel
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource
import com.example.myhome.presentation.features.charge.models.PaymentUiModel
import com.example.myhome.presentation.features.charge.models.SinglePaymentDocumentUiModel
import com.example.myhome.presentation.features.charge.models.networkresults.asChargeGetResource
import com.example.myhome.presentation.features.charge.models.networkresults.asChargeListResource
import com.example.myhome.presentation.features.charge.models.resources.ChargeGetResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeGetViewModel @Inject constructor(
    private val paymentListUseCase: PaymentListUseCase,
    private val spdGetUseCase: SinglePaymentDocumentGetUseCase,
    private val chargeUiMapper: ChargeUiMapper
) : ViewModel() {
    private val _paymentList = MutableLiveData<List<PaymentUiModel>>()
    val paymentList: LiveData<List<PaymentUiModel>> = _paymentList

    private val _spd = MutableLiveData<SinglePaymentDocumentUiModel>()
    val spd: LiveData<SinglePaymentDocumentUiModel> = _spd

    private val _paymentState = MutableLiveData<ChargeListResource>(ChargeListResource.Loading)
    val paymentState: LiveData<ChargeListResource> = _paymentState

    private val _spdState = MutableLiveData<ChargeGetResource>(ChargeGetResource.Loading)
    val spdState: LiveData<ChargeGetResource> = _spdState

    lateinit var chargeParcelable : ChargeListToGetParcelableModel

    fun fetchData() {
        if (chargeParcelable !== null) {
            viewModelScope.launch {
                spdGetUseCase(chargeParcelable.id)
                    .asNetworkResult()
                    .collect {
                        it.asChargeGetResource(_spdState) { data ->
                            _spd.value = chargeUiMapper.spdToUi(data)
                        }
                    }

                paymentListUseCase(chargeParcelable.id)
                    .asNetworkResult()
                    .collect {
                        it.asChargeListResource(_paymentState) { data ->
                            _paymentList.value = chargeUiMapper.paymentListToUi(data)
                        }
                    }
            }
        } else {
            _paymentState.value = ChargeListResource.CodeError
        }
    }
}
