package com.example.myhome.common.mappers

import com.example.myhome.common.dtos.TypeOfServiceGetDto
import com.example.myhome.common.models.TypeOfServiceGetModel

class TypeOfServiceRemoteMapper() {
    fun mapListToDomain(typesOfService: List<TypeOfServiceGetDto>): List<TypeOfServiceGetModel> {
        return typesOfService.map { typeOfService ->
            typeOfService.run {
                TypeOfServiceGetModel(
                    id = id,
                    name = name
                )
            }
        }
    }
}