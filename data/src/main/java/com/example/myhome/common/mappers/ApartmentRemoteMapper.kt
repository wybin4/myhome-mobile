package com.example.myhome.common.mappers

import com.example.myhome.common.dtos.ApartmentGetDto
import com.example.myhome.common.models.ApartmentGetModel

class ApartmentRemoteMapper() {
    fun mapListToDomain(apartments: List<ApartmentGetDto>): List<ApartmentGetModel> {
        return apartments.map { apartment ->
            apartment.run {
                ApartmentGetModel(
                        id = id,
                        name = address,
                        subscriberId = subscriberId
                    )
                }
            }
    }
}
