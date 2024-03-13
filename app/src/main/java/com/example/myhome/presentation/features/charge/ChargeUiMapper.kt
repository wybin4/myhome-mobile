package com.example.myhome.presentation.features.charge

import com.example.myhome.presentation.features.charge.converters.ChargeParcelableConverter
import com.example.myhome.presentation.features.charge.converters.ChargeUiConverter
import javax.inject.Inject

class ChargeUiMapper @Inject constructor(): ChargeUiConverter, ChargeParcelableConverter