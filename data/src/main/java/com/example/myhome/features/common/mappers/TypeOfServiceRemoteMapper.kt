package com.example.myhome.features.common.mappers

import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse
import com.example.myhome.features.common.models.TypeOfServiceListItemModel

class TypeOfServiceRemoteMapper {
    fun mapListToDomain(typesOfService: List<TypeOfServiceListItemResponse>): List<TypeOfServiceListItemModel> {
        return typesOfService.map { typeOfService ->
            typeOfService.run {
                TypeOfServiceListItemModel(
                    id = id,
                    name = name
                )
            }
        }
    }
}