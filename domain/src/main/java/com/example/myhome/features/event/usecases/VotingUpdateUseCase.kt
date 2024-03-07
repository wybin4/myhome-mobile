package com.example.myhome.features.event.usecases

import com.example.myhome.features.event.models.VotingUpdateModel
import kotlinx.coroutines.flow.Flow

interface VotingUpdateUseCase {
    operator fun invoke(vote: VotingUpdateModel): Flow<Boolean>
}