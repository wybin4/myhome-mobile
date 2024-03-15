package com.example.myhome.presentation.features.appeal.add.problem

import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.presentation.features.appeal.AppealMapper
import com.example.myhome.presentation.features.appeal.AppealProblemOrClaimUiModel
import com.example.myhome.presentation.features.appeal.add.BaseAppealProblemOrClaimViewModel
import com.example.myhome.presentation.features.common.CommonUiConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AppealProblemViewModel @Inject constructor(
    private val appealRepository: AppealRepository,
    apartmentRepository: ApartmentRepository,
    subscriberRepository: SubscriberRepository,
    private val appealMapper: AppealMapper,
    commonUiConverter: CommonUiConverter
) : BaseAppealProblemOrClaimViewModel(
    apartmentRepository, subscriberRepository, commonUiConverter,
    AppealType.ProblemOrQuestion
) {
    override fun pickUseCase(appeal: AppealProblemOrClaimUiModel): Flow<Boolean> {
        return appealRepository.problem(appealMapper.problemToRemote(appeal))
    }
}
