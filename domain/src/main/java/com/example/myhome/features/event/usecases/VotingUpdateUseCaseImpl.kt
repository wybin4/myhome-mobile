package com.example.myhome.features.event.usecases

import com.example.myhome.features.event.EventRepository
import com.example.myhome.features.event.models.VotingUpdateModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VotingUpdateUseCaseImpl @Inject constructor(
    private val eventRepository: EventRepository
) : VotingUpdateUseCase {
    override operator fun invoke(vote: VotingUpdateModel): Flow<Boolean> {
        return eventRepository.updateVoting(vote)
    }

}