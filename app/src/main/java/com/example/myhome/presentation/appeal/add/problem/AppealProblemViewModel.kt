package com.example.myhome.presentation.appeal.add.problem

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
class AppealProblemViewModel @Inject constructor(
    private val appealAddUseCase: AppealAddUseCase,
    apartmentListUseCase: ApartmentListUseCase,
    subscriberListUseCase: SubscriberListUseCase
) : BaseAppealProblemOrClaimViewModel(
    apartmentListUseCase, subscriberListUseCase,
    AppealType.ProblemOrQuestion
) {
    override fun pickUseCase(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealAddUseCase.problem(appeal)
    }
}
