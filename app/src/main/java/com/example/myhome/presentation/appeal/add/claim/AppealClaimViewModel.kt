package com.example.myhome.presentation.appeal.add.claim

import com.example.myhome.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.appeal.add.BaseAppealProblemOrClaimViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AppealClaimViewModel @Inject constructor(
    private val appealAddUseCase: AppealAddUseCase,
    apartmentListUseCase: ApartmentListUseCase,
    subscriberListUseCase: SubscriberListUseCase
) : BaseAppealProblemOrClaimViewModel(
    apartmentListUseCase, subscriberListUseCase,
    AppealType.Claim
) {
    override fun pickUseCase(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealAddUseCase.claim(appeal)
    }
}
