package com.example.myhome.presentation.features.appeal.converters

import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.presentation.features.appeal.AppealUiModel

interface AppealUiConverter {
    fun appealListToUi(appeals: List<AppealListItemResponse>): List<AppealUiModel> {
        return appeals.map {
            AppealUiModel(
                id = it.id,
                managementCompanyId = it.managementCompanyId,
                subscriberId = it.subscriberId,
                typeOfAppeal = it.typeOfAppeal,
                status = it.status,
                managementCompanyName = it.name,
                description = it.data,
                attachment = it.attachment,
                createdAt = it.formatCreatedAt()
            )
        }
    }
}