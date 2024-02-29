package com.example.myhome.utils.mappers

import com.example.myhome.appeal.models.AppealGetModel
import com.example.myhome.utils.converters.AppealUiConverter
import com.example.myhome.utils.models.AppealUiModel
import javax.inject.Inject

class AppealUiMapper @Inject constructor(): AppealUiConverter {
    fun mapListToUi(appeals: List<AppealGetModel>): List<AppealUiModel> {
        return appealListToUi(appeals)
    }

}