package com.example.myhome.presentation.features.common

import com.example.myhome.features.common.models.ApartmentListItemModel
import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.common.models.SubscriberUiModel
import com.example.myhome.presentation.features.common.models.TypeOfServiceUiModel

interface AppealUiConverter {
    fun apartmentListToUi(apartments: List<ApartmentListItemModel>): List<ApartmentExtendedUiModel> {
        return apartments.map {
            ApartmentExtendedUiModel(
                id = it.id,
                subscriberId = it.subscriberId,
                name = it.address
            )
        }
    }

    fun typeOfServiceListToUi(typesOfService: List<TypeOfServiceListItemModel>): List<TypeOfServiceUiModel> {
        return typesOfService.map {
            TypeOfServiceUiModel(
                id = it.id,
                name = it.name
            )
        }
    }

    fun subscriberListToUi(subscribers: List<SubscriberListItemModel>): List<SubscriberUiModel> {
        return subscribers.map {
            SubscriberUiModel(
                id = it.subscriberId,
                managementCompanyId = it.managementCompanyId
            )
        }
    }

}