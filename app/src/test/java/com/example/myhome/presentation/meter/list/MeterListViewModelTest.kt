package com.example.myhome.presentation.meter.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.presentation.mappers.MeterUiMapper
import com.example.myhome.presentation.models.ApartmentUiModel
import com.example.myhome.presentation.models.MeterUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.any
import java.util.Date

@ExperimentalCoroutinesApi
class MeterListViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var apartmentWithMeterListUseCase: ApartmentWithMeterListUseCase
    private lateinit var meterUiMapper: MeterUiMapper
    private lateinit var viewModel: MeterListViewModel

    private val dispatcher = StandardTestDispatcher()

    private val date = Date()
    private val address = "Советский союз"

    private val testApartmentUi = listOf(
        ApartmentUiModel(1, address, false),
        ApartmentUiModel(2, address, false)
    )

    private val testMetersUi = listOf(
        MeterUiModel(1, "12332132131231", date, date, 1, address, 16.9, "Газ", "м3", false),
        MeterUiModel(2, "12332132131232", date, date, 2, address, 12.0, "ХВС", "м3", false)
    )

    private val testMeters = listOf(
        MeterGetModel(1, "12332132131231", date, date, 1, "Газ", 16.9, "м3"),
        MeterGetModel(2, "12332132131232", date, date, 2, "ХВС", 12.0, "м3")
    )

    private val testApartments = listOf(
        ApartmentWithMeterGetModel(1, address, testMeters),
        ApartmentWithMeterGetModel(2, address, emptyList())
    )

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        meterUiMapper = Mockito.mock(MeterUiMapper::class.java)
        apartmentWithMeterListUseCase = Mockito.mock(ApartmentWithMeterListUseCase::class.java)

        Mockito.`when`(meterUiMapper.mapMeterListToUi(any())).thenReturn(testMetersUi)
        Mockito.`when`(meterUiMapper.mapApartmentListToUi(any())).thenReturn(testApartmentUi)

        viewModel = MeterListViewModel(apartmentWithMeterListUseCase, meterUiMapper)
        viewModel.setupLists(testApartments)
    }

    @Test
    fun `selectedApartmentId contains first item id after setupLists`() {
        val expected = testApartments.first().id
        val actual = viewModel.selectedApartmentId
        assertEquals(expected, actual)
    }

    @Test
    fun `meterList contains correct items after initialization`() {
        assertEquals(testMetersUi, viewModel.meterList.value)

        val expected = testMetersUi.map { it.setIsIssued() }
        assertEquals(expected, viewModel.meterList.value) // isIssued?
    }

    @Test
    fun `apartmentList contains correct items after initialization`() {
        assertEquals(testApartmentUi, viewModel.apartmentList.value)

        val fstVal = testApartments.first().id
        val selectCheckResult = viewModel.apartmentList.value?.filter {
            if (it.id == fstVal) { // условие наоборот, чтобы отсечь объекты
                !it.selected
            } else {
                it.selected
            }
        }
        assertEquals(emptyList<ApartmentUiModel>(), selectCheckResult) // selected?
    }

    @Test
    fun `selectedApartmentId change correct after changeSelectedApartment`() {
        val expected = testApartmentUi[1].id
        viewModel.changeSelectedApartment(testApartmentUi[1])
        val actual = viewModel.selectedApartmentId
        assertEquals(expected, actual)
    }

    @Test
    fun `apartmentList change correct after changeSelectedApartment`() {
        val value = testApartmentUi[1]
        viewModel.changeSelectedApartment(value)

        val selectCheckResult = viewModel.apartmentList.value?.let { apartments ->
            apartments.filter {
                if (it.id == value.id) {
                    !it.selected
                } else {
                    it.selected
                }
            }
        }

        assertEquals(emptyList<ApartmentUiModel>(), selectCheckResult)
    }

    @Test
    fun `meterList change correct after changeSelectedApartment`() {
        val value = testApartmentUi[1]
        viewModel.changeSelectedApartment(value)

        val expected = testMetersUi.map { it.setIsIssued() }
        assertEquals(expected, viewModel.meterList.value)
    }

    @Test
    fun `meterList set empty when apartment isn't find while changeSelectedApartment`() {
        viewModel.changeSelectedApartment(ApartmentUiModel(3, address, false))

        assertEquals(emptyList<MeterUiModel>(), viewModel.meterList.value)
    }

}