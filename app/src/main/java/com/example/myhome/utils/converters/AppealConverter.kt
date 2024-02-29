package com.example.myhome.utils.converters

import com.example.myhome.appeal.models.AppealGetModel
import com.example.myhome.appeal.models.AppealStatus
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.utils.models.AppealUiModel

interface AppealUiConverter {
    fun appealListToUi(appeals: List<AppealGetModel>): List<AppealUiModel> {
        return appeals.map {
            AppealUiModel(
                id = it.id,
                managementCompanyId = it.managementCompanyId,
                subscriberId = it.subscriberId,
                typeOfAppeal = it.typeOfAppeal,
                status = it.status,
                managementCompanyName = it.managementCompanyName,
                description = it.description,
                attachment = it.attachment,
                createdAt = it.createdAt
            )
        }
    }

    fun getStatus(status: AppealStatus): String {
        return when (status) {
            AppealStatus.Processing -> "В обработке"
            AppealStatus.Closed -> "Обработано"
            AppealStatus.Rejected -> "Отказ"
            else -> ""
        }
    }

    fun getType(typeOfAppeal: AppealType): String {
        return when (typeOfAppeal) {
            AppealType.AddIndividualMeter -> "Замена счётчика"
            AppealType.VerifyIndividualMeter -> "Поверка счётчика"
            AppealType.ProblemOrQuestion -> "Вопрос"
            AppealType.Claim -> "Претензия"
            else -> "Обращение"
        }
    }

}