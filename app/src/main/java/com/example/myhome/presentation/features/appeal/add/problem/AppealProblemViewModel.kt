package com.example.myhome.presentation.features.appeal.add.problem

import com.example.myhome.features.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.appeal.usecases.AppealAddUseCase
import com.example.myhome.features.common.usecases.ApartmentListUseCase
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.features.appeal.add.BaseAppealProblemOrClaimViewModel
import com.example.myhome.presentation.features.common.CommonUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AppealProblemViewModel @Inject constructor(
    private val appealAddUseCase: AppealAddUseCase,
    apartmentListUseCase: ApartmentListUseCase,
    subscriberListUseCase: SubscriberListUseCase,
    commonUiMapper: CommonUiMapper
) : BaseAppealProblemOrClaimViewModel(
    apartmentListUseCase, subscriberListUseCase, commonUiMapper,
    AppealType.ProblemOrQuestion
) {
    override fun pickUseCase(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealAddUseCase.problem(appeal)
    }
}