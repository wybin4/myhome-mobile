package com.example.myhome.presentation.features.appeal.converters

import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.presentation.features.appeal.AppealUiModel

interface AppealUiConverter {
    fun appealToUi(appeal: AppealListItemResponse): AppealUiModel {
        return appeal.run {
            AppealUiModel(
                id = id,
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                typeOfAppeal = typeOfAppeal,
                status = status,
                managementCompanyName = name,
                description = data,
                attachment = attachment,
                createdAt = formatCreatedAt(),
            )
        }
    }
}