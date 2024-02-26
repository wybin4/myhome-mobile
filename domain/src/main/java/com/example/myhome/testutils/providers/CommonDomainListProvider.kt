package com.example.myhome.testutils.providers

import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.apartmentList

public object CommonDomainTestListProvider {
    fun getApartmentList(): List<ApartmentGetModel> {
        return apartmentList.map {
            ApartmentGetModel(
                id = it.id,
                name = it.address,
                subscriberId = it.id
            )
        }
    }

    fun getTypeOfServiceList(): List<TypeOfServiceGetModel> {
        return listOf(
            TypeOfServiceGetModel(
                id = 1, name = "Газ",
            ),
            TypeOfServiceGetModel(
                id = 2, name = "ХВС",
            ),
            TypeOfServiceGetModel(
                id = 3, name = "ГВС",
            ),
            TypeOfServiceGetModel(
                id = 4, name = "Электроэнергия",
            ),
            TypeOfServiceGetModel(
                id = 4, name = "Отопление",
            ),
        )
    }

    fun getSubscriberList(): List<SubscriberGetModel> {
        return listOf(
            SubscriberGetModel(
                managementCompanyId = 1, subscriberId = 1,
            ),
            SubscriberGetModel(
                managementCompanyId = 1, subscriberId = 2,
            )
        )
    }

}
