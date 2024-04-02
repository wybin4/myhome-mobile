package com.example.myhome.presentation.utils.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myhome.models.DateConverter
import com.example.myhome.models.FilterListItemRequest
import java.util.Date

class FilterObserveManager<T : Enum<T>>(
    private val onFilter: (MutableList<FilterListItemRequest>) -> Unit,
    private val startList: List<T>,
    private val secondFieldName: String,
): DateConverter {
    val filters = mutableListOf<FilterListItemRequest>()

    private val _selectCreatedAt = MutableLiveData<Pair<Date?, Date?>>()
    private val selectCreatedAt: LiveData<Pair<Date?, Date?>> = _selectCreatedAt
    fun setCreatedAt(date1: Date?, date2: Date?) {
        _selectCreatedAt.value = Pair(date1, date2)
    }

    private val _selectSecondList = MutableLiveData(startList)
    private val selectSecondList: LiveData<List<T>> = _selectSecondList
    fun setSecondList(statusList: List<T>) {
        _selectSecondList.value = statusList
    }
    fun getSecondList(): List<T> {
        return selectSecondList.value!!
    }

    fun setup() {
        val observeFilters: () -> Unit = {
            val createdAtFilter = filters.find { it.filterField == "createdAt" }
            if (selectCreatedAt.value != null) {
                val datePair = selectCreatedAt.value
                if (datePair?.first != null && datePair.second != null) {
                    val startDateString = formatDateDash(datePair.first!!)
                    val endDateString = formatDateDash(datePair.second!!)
                    if (createdAtFilter != null) {
                        createdAtFilter.filterArray = listOf(startDateString, endDateString)
                    } else {
                        filters.add(
                            FilterListItemRequest(
                                filterField = "createdAt",
                                filterArray = listOf(startDateString, endDateString)
                            )
                        )
                    }
                }
            }
            val statusFilter = filters.find { it.filterField == secondFieldName }
            val statusStringList = selectSecondList.value?.map { it.name } ?: emptyList()
            if (selectSecondList.value != startList) {
                if (statusFilter != null) {
                    statusFilter.filterArray = statusStringList
                } else {
                    filters.add(
                        FilterListItemRequest(
                            filterField = secondFieldName,
                            filterArray = statusStringList
                        )
                    )
                }
            } else {
                filters.removeAll { it.filterField == secondFieldName }
            }
            onFilter(filters)
        }
        selectSecondList.observeForever { observeFilters.invoke() }
        selectCreatedAt.observeForever { observeFilters.invoke() }
    }
}
