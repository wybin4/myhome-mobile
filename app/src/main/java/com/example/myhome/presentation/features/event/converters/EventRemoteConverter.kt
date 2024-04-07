package com.example.myhome.presentation.features.event.converters

import com.example.myhome.features.event.dto.VotingUpdateRequest

interface EventRemoteConverter {
    fun votingToRemote(optionId: Int): VotingUpdateRequest {
        return VotingUpdateRequest(
            optionId = optionId
        )
    }
}