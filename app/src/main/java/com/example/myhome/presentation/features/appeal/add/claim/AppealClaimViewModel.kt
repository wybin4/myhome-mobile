package com.example.myhome.presentation.features.appeal.add.claim

import com.example.myhome.features.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.appeal.usecases.AppealAddUseCase
import com.example.myhome.features.common.usecases.ApartmentListUseCase
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.features.appeal.add.BaseAppealProblemOrClaimViewModel
import com.example.myhome.presentation.features.common.CommonUiConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AppealClaimViewModel @Inject constructor(
    private val appealAddUseCase: AppealAddUseCase,
    apartmentListUseCase: ApartmentListUseCase,
    subscriberListUseCase: SubscriberListUseCase,
    commonUiConverter: CommonUiConverter
) : BaseAppealProblemOrClaimViewModel(
    apartmentListUseCase, subscriberListUseCase, commonUiConverter,
    AppealType.Claim
) {
    override fun pickUseCase(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealAddUseCase.claim(appeal)
    }
}
