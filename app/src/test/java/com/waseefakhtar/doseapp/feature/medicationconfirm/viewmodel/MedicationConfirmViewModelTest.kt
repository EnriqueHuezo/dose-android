package com.waseefakhtar.doseapp.feature.medicationconfirm.viewmodel

import com.waseefakhtar.doseapp.MedicationNotificationService
import com.waseefakhtar.doseapp.analytics.AnalyticsHelper
import com.waseefakhtar.doseapp.domain.model.Medication
import com.waseefakhtar.doseapp.feature.medicationconfirm.usecase.AddMedicationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class MedicationConfirmViewModelTest {
    private val addMedicationUseCase = mock(AddMedicationUseCase::class.java)
    private val medicationNotificationService = mock(MedicationNotificationService::class.java)
    private val analyticsHelper = mock(AnalyticsHelper::class.java)
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MedicationConfirmViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MedicationConfirmViewModel(
            addMedicationUseCase,
            medicationNotificationService,
            analyticsHelper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addMedication calls addMedicationUseCase and schedules notifications`() = runTest {
        val medication1 = mock(Medication::class.java)
        val medication2 = mock(Medication::class.java)
        val medications = listOf(medication1, medication2)
        val state = MedicationConfirmState(medications = medications)
        `when`(addMedicationUseCase.addMedication(medications)).thenReturn(flow { emit(medications) })

        viewModel.addMedication(state)
        dispatcher.scheduler.advanceUntilIdle()

        verify(addMedicationUseCase).addMedication(medications)
        verify(medicationNotificationService).scheduleNotification(medication1, analyticsHelper)
        verify(medicationNotificationService).scheduleNotification(medication2, analyticsHelper)
    }

    @Test
    fun `addMedication emits isMedicationSaved event`() = runTest {
        val medication = mock(Medication::class.java)
        val medications = listOf(medication)
        val state = MedicationConfirmState(medications = medications)
        `when`(addMedicationUseCase.addMedication(medications)).thenReturn(flow { emit(medications) })

        val results = mutableListOf<Unit>()
        val job = launch {
            viewModel.isMedicationSaved.collect { results.add(it) }
        }

        viewModel.addMedication(state)
        dispatcher.scheduler.advanceUntilIdle()

        assert(results.isNotEmpty())
        job.cancel()
    }

    @Test
    fun `logEvent calls analyticsHelper logEvent`() {
        viewModel.logEvent("test_event")
        verify(analyticsHelper).logEvent(eventName = "test_event")
    }
}
