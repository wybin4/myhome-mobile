package com.example.myhome.presentation.features.common

import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.common.models.SubscriberUiModel
import com.example.myhome.presentation.features.common.models.TypeOfServiceUiModel
import javax.inject.Inject

class CommonUiConverter @Inject constructor() {
    fun apartmentListToUi(apartments: List<ApartmentListItemResponse>): List<ApartmentExtendedUiModel> {
        return apartments.map {
            ApartmentExtendedUiModel(
                id = it.id,
                subscriberId = it.subscriberId,
                name = it.address
            )
        }
    }

    fun typeOfServiceListToUi(typesOfService: List<TypeOfServiceListItemResponse>): List<TypeOfServiceUiModel> {
        return typesOfService.map {
            TypeOfServiceUiModel(
                id = it.id,
                name = it.name
            )
        }
    }

    fun subscriberListToUi(subscribers: List<SubscriberListItemResponse>): List<SubscriberUiModel> {
        return subscribers.map {
            SubscriberUiModel(
                id = it.subscriberId,
                managementCompanyId = it.managementCompanyId
            )
        }
    }

}