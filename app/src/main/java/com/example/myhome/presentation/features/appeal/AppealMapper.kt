package com.example.myhome.presentation.features.appeal

import com.example.myhome.presentation.features.appeal.converters.AppealRemoteConverter
import com.example.myhome.presentation.features.appeal.converters.AppealUiConverter
import javax.inject.Inject

class AppealMapper @Inject constructor(): AppealUiConverter, AppealRemoteConverter