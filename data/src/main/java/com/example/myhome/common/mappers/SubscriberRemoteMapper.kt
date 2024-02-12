package com.example.myhome.common.mappers

import com.example.myhome.common.dtos.SubscriberGetDto
import com.example.myhome.common.models.SubscriberGetModel

class SubscriberRemoteMapper() {
    fun mapListToDomain(subscribers: List<SubscriberGetDto>): List<SubscriberGetModel> {
        return subscribers.map { subscriber ->
            subscriber.run {
                SubscriberGetModel(
                        subscriberId = subscriberId,
                        managementCompanyId = managementCompanyId
                    )
                }
            }
    }
}
