package com.example.myhome.features.common.mappers

import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import com.example.myhome.features.common.models.SubscriberListItemModel

class SubscriberRemoteMapper {
    fun mapListToDomain(subscribers: List<SubscriberListItemResponse>): List<SubscriberListItemModel> {
        return subscribers.map { subscriber ->
            subscriber.run {
                SubscriberListItemModel(
                        subscriberId = subscriberId,
                        managementCompanyId = managementCompanyId
                    )
                }
            }
    }
}
