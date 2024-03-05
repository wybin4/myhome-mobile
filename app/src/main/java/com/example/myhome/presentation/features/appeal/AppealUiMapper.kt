package com.example.myhome.presentation.features.appeal

import com.example.myhome.features.appeal.models.AppealListItemModel
import javax.inject.Inject

class AppealUiMapper @Inject constructor(): AppealUiConverter {
    fun mapListToUi(appeals: List<AppealListItemModel>): List<AppealUiModel> {
        return appealListToUi(appeals)
    }

}