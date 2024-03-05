package com.example.myhome.testutils.providers

import com.example.myhome.features.common.models.ApartmentListItemModel
import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.apartmentList

object CommonDomainTestListProvider {
    fun getApartmentList(): List<ApartmentListItemModel> {
        return apartmentList.map {
            ApartmentListItemModel(
                id = it.id,
                address = it.address,
                subscriberId = it.id
            )
        }
    }

    fun getTypeOfServiceList(): List<TypeOfServiceListItemModel> {
        return listOf(
            TypeOfServiceListItemModel(
                id = 1, name = "Газ",
            ),
            TypeOfServiceListItemModel(
                id = 2, name = "ХВС",
            ),
            TypeOfServiceListItemModel(
                id = 3, name = "ГВС",
            ),
            TypeOfServiceListItemModel(
                id = 4, name = "Электроэнергия",
            ),
            TypeOfServiceListItemModel(
                id = 4, name = "Отопление",
            ),
        )
    }

    fun getSubscriberList(): List<SubscriberListItemModel> {
        return listOf(
            SubscriberListItemModel(
                managementCompanyId = 1, subscriberId = 1,
            ),
            SubscriberListItemModel(
                managementCompanyId = 1, subscriberId = 2,
            )
        )
    }

}
