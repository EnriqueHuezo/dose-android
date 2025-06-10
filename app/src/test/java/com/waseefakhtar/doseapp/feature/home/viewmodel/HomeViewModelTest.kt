package com.waseefakhtar.doseapp.feature.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.waseefakhtar.doseapp.analytics.AnalyticsHelper
import com.waseefakhtar.doseapp.extension.toFormattedYearMonthDateString
import com.waseefakhtar.doseapp.feature.home.model.CalendarModel
import com.waseefakhtar.doseapp.feature.home.usecase.GetMedicationsUseCase
import com.waseefakhtar.doseapp.feature.home.usecase.UpdateMedicationUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.Date

class HomeViewModelTest {
    private lateinit var getMedicationsUseCase: GetMedicationsUseCase
    private lateinit var updateMedicationUseCase: UpdateMedicationUseCase
    private lateinit var analyticsHelper: AnalyticsHelper
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        getMedicationsUseCase = mock(GetMedicationsUseCase::class.java)
        updateMedicationUseCase = mock(UpdateMedicationUseCase::class.java)
        analyticsHelper = mock(AnalyticsHelper::class.java)
        savedStateHandle = SavedStateHandle()
        viewModel = HomeViewModel(
            getMedicationsUseCase,
            updateMedicationUseCase,
            savedStateHandle,
            analyticsHelper
        )
    }

    @Test
    fun `updateSelectedDate actualiza el filtro de fecha`() {
        val date = Date(0)
        viewModel.updateSelectedDate(date)
        assertEquals(
            date.toFormattedYearMonthDateString(),
            savedStateHandle[HomeViewModel.DATE_FILTER_KEY]
        )
    }

    @Test
    fun `selectDate actualiza el filtro de fecha`() {
        val dateModel = CalendarModel.DateModel(Date(123456789), false, false)
        viewModel.selectDate(dateModel)
        assertEquals(
            dateModel.date.toFormattedYearMonthDateString(),
            savedStateHandle[HomeViewModel.DATE_FILTER_KEY]
        )
    }

    @Test
    fun `logEvent llama a analyticsHelper`() {
        val eventName = "test_event"
        viewModel.logEvent(eventName)
        verify(analyticsHelper).logEvent(eventName = eventName)
    }
}
