package com.example.myhome.presentation.utils.pickers

import com.example.myhome.R
import com.example.myhome.features.appeal.models.AppealStatus
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.presentation.features.common.models.TypeOfServiceEngNames

interface IconPicker {
    fun getMeterIcon(name: String): Int? {
        return when (name.replaceFirstChar { it.uppercaseChar() }) {
            TypeOfServiceEngNames.Gas.toString() -> R.drawable.gas
            TypeOfServiceEngNames.Electricity.toString() -> R.drawable.electricity
            TypeOfServiceEngNames.Heat.toString() -> R.drawable.heat
            TypeOfServiceEngNames.Water.toString() -> R.drawable.water
            else -> null
        }
    }

    fun getAppealTypeIcon(type: AppealType): Int? {
        return when (type) {
            AppealType.AddIndividualMeter -> R.drawable.meter
            AppealType.VerifyIndividualMeter -> R.drawable.meter
            AppealType.ProblemOrQuestion -> R.drawable.problem_or_question
            AppealType.Claim -> R.drawable.claim
            else -> null
        }
    }

    fun getAppealStatusIcon(status: AppealStatus): Int? {
        return when (status) {
            AppealStatus.Processing -> R.drawable.processing
            AppealStatus.Closed -> R.drawable.closed
            AppealStatus.Rejected -> R.drawable.rejected
            else -> null
        }
    }
}
