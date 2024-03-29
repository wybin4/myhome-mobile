package com.example.myhome.presentation.utils.pickers

import com.example.myhome.R
import com.example.myhome.features.appeal.models.AppealStatus
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.charge.dtos.AmountChange
import com.example.myhome.presentation.features.common.models.TypeOfServiceEngNames
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiType

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
            AppealType.AddIndividualMeter -> R.drawable.meters_logo
            AppealType.VerifyIndividualMeter -> R.drawable.meters_logo
            AppealType.ProblemOrQuestion -> R.drawable.problem_or_question
            AppealType.Claim -> R.drawable.events_logo
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

    fun getAmountChangeIcon(amountChange: AmountChange): Int? {
        return when (amountChange) {
            AmountChange.Positive -> R.drawable.arrow_top
            AmountChange.Negative -> R.drawable.arrow_bottom
            else -> null
        }
    }

    fun getServiceNotificationIcon(type: ServiceNotificationUiType): Int {
        return when (type) {
            ServiceNotificationUiType.System -> R.drawable.system_warning
            ServiceNotificationUiType.User -> R.drawable.user_warning
            else -> R.drawable.question
        }
    }

}
