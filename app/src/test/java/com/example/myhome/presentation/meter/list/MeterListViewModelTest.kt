package com.example.myhome.presentation.meter.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.testutils.providers.MeterUITestListProvider.getApartmentUiList
import com.example.myhome.utils.mappers.MeterUiMapper
import com.example.myhome.utils.models.ApartmentUiModel
import com.example.myhome.utils.models.MeterUiModel
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.apartmentList
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.getMeterList
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterUiList
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
    private lateinit var meterUiMapper: MeterUiMapper
    private lateinit var viewModel: MeterListViewModel

    private val dispatcher = StandardTestDispatcher()
    private val testApartmentUi = getApartmentUiList()
    private val testMetersUi = getMeterUiList()
    private val testApartments = apartmentList

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
        viewModel.changeSelectedApartment(ApartmentUiModel(3, "Нет!", false))

        assertEquals(emptyList<MeterUiModel>(), viewModel.meterList.value)
    }

}