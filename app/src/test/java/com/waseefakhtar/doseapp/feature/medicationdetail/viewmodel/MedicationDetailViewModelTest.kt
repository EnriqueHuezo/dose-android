package com.waseefakhtar.doseapp.feature.medicationdetail.viewmodel

import com.waseefakhtar.doseapp.analytics.AnalyticsHelper
import com.waseefakhtar.doseapp.domain.model.Medication
import com.waseefakhtar.doseapp.feature.home.usecase.UpdateMedicationUseCase
import com.waseefakhtar.doseapp.feature.medicationdetail.usecase.GetMedicationUseCase
import com.waseefakhtar.doseapp.util.MedicationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class MedicationDetailViewModelTest {
    private val getMedicationUseCase = mock(GetMedicationUseCase::class.java)
    private val updateMedicationUseCase = mock(UpdateMedicationUseCase::class.java)
    private val analyticsHelper = mock(AnalyticsHelper::class.java)
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MedicationDetailViewModel

    private val medication = Medication(
        id = 1,
        name = "Aspirin",
        dosage = 100,
        frequency = "Daily",
        startDate = java.util.Date(),
        endDate = java.util.Date(),
        medicationTaken = false,
        medicationTime = java.util.Date(),
        type = MedicationType.TABLET
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MedicationDetailViewModel(
            getMedicationUseCase,
            updateMedicationUseCase,
            analyticsHelper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMedicationById updates medication state`() = runTest {
        `when`(getMedicationUseCase(1L)).thenReturn(medication)

        viewModel.getMedicationById(1L)
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(medication, viewModel.medication.value)
    }

    @Test
    fun `updateMedication calls updateMedicationUseCase with updated medication`() = runTest {
        viewModel.updateMedication(medication, true)
        dispatcher.scheduler.advanceUntilIdle()

        verify(updateMedicationUseCase).updateMedication(medication.copy(medicationTaken = true))
    }

    @Test
    fun `logEvent calls analyticsHelper logEvent`() {
        viewModel.logEvent("test_event")
        verify(analyticsHelper).logEvent(eventName = "test_event")
    }
}
