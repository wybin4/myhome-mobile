package com.example.myhome.presentation.features.meter.get

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.meter.models.ReadingListItemModel
import com.example.myhome.features.meter.usecases.ReadingListUseCase
import com.example.myhome.presentation.features.meter.MeterGetToScanParcelableModel
import com.example.myhome.presentation.features.meter.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.features.meter.MeterListToGetParcelableModel
import com.example.myhome.presentation.features.meter.MeterUiMapper
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterGetViewModel @Inject constructor(
    private val readingListUseCase: ReadingListUseCase,
    private val meterUiMapper: MeterUiMapper
) : ViewModel() {
    private val _readingList = MutableLiveData<List<ReadingListItemModel>>()
    val readingList: LiveData<List<ReadingListItemModel>> = _readingList

    private val _readingListState = MutableLiveData<Resource>(Resource.Loading)
    val readingListState: LiveData<Resource> = _readingListState

    lateinit var meterParcelable : MeterListToGetParcelableModel

    fun mapMeterGetToScanParcel(meter: MeterListToGetParcelableModel, prev: Double): MeterGetToScanParcelableModel {
        return meterUiMapper.mapMeterGetToScanParcel(meter, prev)
    }

    fun mapMeterGetToUpdateParcel(meter: MeterListToGetParcelableModel): MeterGetToUpdateParcelableModel {
        return meterUiMapper.mapMeterGetToUpdateParcel(meter)
    }

    fun fetchReadingList() {
        if (meterParcelable !== null) {
            viewModelScope.launch {
                readingListUseCase(meterParcelable.id)
                    .asNetworkResult()
                    .collect {
                        it.asListResource(_readingListState) { data ->
                            _readingList.value = data
                        }
                    }
            }
        } else {
            TODO("Ошибка!")
        }
    }
}
