package com.example.myhome.features.common.mappers

import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import com.example.myhome.features.common.models.ApartmentListItemModel

class ApartmentRemoteMapper {
    fun mapListToDomain(apartments: List<ApartmentListItemResponse>): List<ApartmentListItemModel> {
        return apartments.map { apartment ->
            apartment.run {
                ApartmentListItemModel(
                        id = id,
                        address = address,
                        subscriberId = subscriberId
                    )
                }
            }
    }
}
