package com.example.myhome.testutils

import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse
import com.example.myhome.testutils.MeterDomainTestListProvider.apartmentList

object CommonDomainTestListProvider {
    fun getApartmentList(): List<ApartmentListItemResponse> {
        return apartmentList.map {
            ApartmentListItemResponse(
                id = it.apartmentId,
                houseId = 1,
                apartmentNumber = 1,
                totalArea = 0.0,
                livingArea = 0.0,
                numberOfRegistered = 0,
                address = it.apartmentFullAddress,
                subscriberId = it.apartmentId
            )
        }
    }

    fun getTypeOfServiceList(): List<TypeOfServiceListItemResponse> {
        return listOf(
            TypeOfServiceListItemResponse(
                id = 1, name = "Газ", unitId = 1, engName = "No"
            ),
            TypeOfServiceListItemResponse(
                id = 2, name = "ХВС", unitId = 1, engName = "No"
            ),
            TypeOfServiceListItemResponse(
                id = 3, name = "ГВС", unitId = 1, engName = "No"
            ),
            TypeOfServiceListItemResponse(
                id = 4, name = "Электроэнергия", unitId = 1, engName = "No"
            ),
            TypeOfServiceListItemResponse(
                id = 4, name = "Отопление", unitId = 1, engName = "No"
            ),
        )
    }

    fun getSubscriberList(): List<SubscriberListItemResponse> {
        return listOf(
            SubscriberListItemResponse(
                managementCompanyId = 1, subscriberId = 1, address = ""
            ),
            SubscriberListItemResponse(
                managementCompanyId = 1, subscriberId = 2, address = ""
            )
        )
    }

}
