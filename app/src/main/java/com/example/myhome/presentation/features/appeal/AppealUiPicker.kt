package com.example.myhome.presentation.features.appeal

import com.example.myhome.features.appeal.models.AppealStatus
import com.example.myhome.features.appeal.models.AppealType

interface AppealUiPicker {
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