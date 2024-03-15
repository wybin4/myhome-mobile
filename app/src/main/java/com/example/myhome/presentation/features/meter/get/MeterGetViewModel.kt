package com.example.myhome.presentation.features.meter.get

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.meter.repositories.ReadingRepository
import com.example.myhome.presentation.features.meter.MeterGetToScanParcelableModel
import com.example.myhome.presentation.features.meter.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.features.meter.MeterListToGetParcelableModel
import com.example.myhome.presentation.features.meter.mappers.MeterMapper
import com.example.myhome.presentation.features.meter.ReadingUiModel
import com.example.myhome.presentation.features.meter.mappers.ReadingMapper
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterGetViewModel @Inject constructor(
    private val readingRepository: ReadingRepository,
    private val meterMapper: MeterMapper,
    private val readingMapper: ReadingMapper
) : ViewModel() {
    private val _readingList = MutableLiveData<List<ReadingUiModel>>()
    val readingList: LiveData<List<ReadingUiModel>> = _readingList

    private val _readingListState = MutableLiveData<Resource>(Resource.Loading)
    val readingListState: LiveData<Resource> = _readingListState

    lateinit var meterParcelable : MeterListToGetParcelableModel

    fun mapMeterGetToScanParcel(meter: MeterListToGetParcelableModel, prev: Double): MeterGetToScanParcelableModel {
        return meterMapper.meterGetToScanParcel(meter, prev)
    }

    fun mapMeterGetToUpdateParcel(meter: MeterListToGetParcelableModel): MeterGetToUpdateParcelableModel {
        return meterMapper.meterGetToUpdateParcel(meter)
    }

    fun fetchReadingList() {
        if (meterParcelable !== null) {
            viewModelScope.launch {
                readingRepository.listReading(meterParcelable.id)
                    .asNetworkResult()
                    .collect {
                        it.asListResource(_readingListState) { data ->
                            _readingList.value = readingMapper.readingListToUi(data, meterParcelable.unitName)
                        }
                    }
            }
        } else {
            _readingListState.value = Resource.Empty
        }
    }
}
