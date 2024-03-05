package com.example.myhome.presentation.features.common

import com.example.myhome.features.common.models.ApartmentListItemModel
import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.common.models.SubscriberUiModel
import com.example.myhome.presentation.features.common.models.TypeOfServiceUiModel
import javax.inject.Inject

class CommonUiMapper @Inject constructor(): AppealUiConverter {
    fun mapApartmentListToUi(apartments: List<ApartmentListItemModel>): List<ApartmentExtendedUiModel> {
        return apartmentListToUi(apartments)
    }

    fun mapTypeOfServiceListToUi(typesOfService: List<TypeOfServiceListItemModel>): List<TypeOfServiceUiModel> {
        return typeOfServiceListToUi(typesOfService)
    }

    fun mapSubscriberListToUi(subscribers: List<SubscriberListItemModel>): List<SubscriberUiModel> {
        return subscriberListToUi(subscribers)
    }

}