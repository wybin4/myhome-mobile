package com.example.myhome.presentation.meter.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myhome.presentation.features.meter.ApartmentUiModel
import com.example.myhome.presentation.features.meter.mappers.MeterMapper
import com.example.myhome.presentation.features.meter.MeterUiModel
import com.example.myhome.presentation.features.meter.list.MeterListViewModel
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider.getApartmentUiList
import com.example.myhome.testutils.MeterDomainTestListProvider.apartmentList
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider.getMeterUiList
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

@ExperimentalCoroutinesApi
class MeterListViewModelTest {
    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var apartmentWithMeterListUseCase: ApartmentWithMeterListUseCase
    private lateinit var meterMapper: MeterMapper
    private lateinit var viewModel: MeterListViewModel

    private val dispatcher = StandardTestDispatcher()
    private val testApartmentUi = getApartmentUiList()
    private val testMetersUi = getMeterUiList()
    private val testApartments = apartmentList

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        meterMapper = Mockito.mock(MeterMapper::class.java)
        apartmentWithMeterListUseCase = Mockito.mock(ApartmentWithMeterListUseCase::class.java)

        Mockito.`when`(meterMapper.apartmentWithMeterToUi(any())).thenReturn(testMetersUi)
        Mockito.`when`(meterMapper.apartmentListToUi(any())).thenReturn(testApartmentUi)

        viewModel = MeterListViewModel(apartmentWithMeterListUseCase, meterMapper)
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
        viewModel.changeSelectedApartment(ApartmentUiModel(3, "Нет!", false))

        assertEquals(emptyList<MeterUiModel>(), viewModel.meterList.value)
    }

}