package com.example.myhome.presentation.features.meter.scan

import androidx.lifecycle.viewModelScope
import com.example.myhome.features.meter.models.ReadingAddModel
import com.example.myhome.features.meter.usecases.ReadingAddUseCase
import com.example.myhome.presentation.BaseDigitPickerViewModel
import com.example.myhome.presentation.features.meter.MeterGetToScanParcelableModel
import com.example.myhome.presentation.models.asAddResource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.utils.pickers.IconPicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MeterScanViewModel @Inject constructor(
    private val readingAddUseCase: ReadingAddUseCase
) : BaseDigitPickerViewModel(), IconPicker {
    lateinit var meterParcelable : MeterGetToScanParcelableModel

     fun addNewValue(newValue: Double) {
        viewModelScope.launch {
            readingAddUseCase(
                ReadingAddModel(
                    meterId = meterParcelable.meterId,
                    reading = newValue,
                    readAt = Date()
                )
            )
                .asNetworkResult()
                .collect { manageAddState(it) }
        }
    }

    override fun isCardLeftTextVisible(): Boolean = false

    override fun getCardLeftText(): String = ""

    override fun isIcon(): Boolean = true

    override fun getIconText(): String = ""

    override fun getIcon(): Int? = getMeterIcon(meterParcelable.typeOfServiceEngName)

    override fun getTitle(): String = meterParcelable.address

    override fun getSubtitle(): String = meterParcelable.typeOfServiceName

    override fun getRightTitle(): String = "Предыдущие показания"

    override fun getLeftTitle(): String = "Потребление"
    override fun getPrevValue(): Double = meterParcelable.previousReading

    override fun getRightText(): String {
        return "${meterParcelable.previousReading} ${meterParcelable.unitName}"
    }

    override fun getUnit(): String = meterParcelable.unitName
}