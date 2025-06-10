package com.waseefakhtar.doseapp.feature.addmedication.viewmodel

import android.content.Context
import com.waseefakhtar.doseapp.analytics.AnalyticsHelper
import com.waseefakhtar.doseapp.feature.addmedication.model.CalendarInformation
import com.waseefakhtar.doseapp.util.Frequency
import com.waseefakhtar.doseapp.util.MedicationType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.util.Calendar
import java.util.Date

class AddMedicationViewModelTest {
    private lateinit var analyticsHelper: AnalyticsHelper
    private lateinit var context: Context
    private lateinit var viewModel: AddMedicationViewModel

    @Before
    fun setUp() {
        analyticsHelper = mock(AnalyticsHelper::class.java)
        context = mock(Context::class.java)
        viewModel = AddMedicationViewModel(analyticsHelper, context)
    }

    @Test
    fun `createMedications returns correct number of medications`() {
        val name = "Ibuprofeno"
        val dosage = 200
        val frequency = Frequency.EVERYDAY.name
        val startDate = Date(0)
        val endDate = Date(2 * 86400 * 1000L) // 2 days later
        val medicationTimes = listOf(
            CalendarInformation(
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 8)
                    set(Calendar.MINUTE, 0)
                }
            )
        )
        val type = MedicationType.TABLET

        `when`(
            context.getString(
                Frequency.EVERYDAY.stringResId,
                Frequency.EVERYDAY.days
            )
        ).thenReturn("Cada 1 día(s)")

        val medications = viewModel.createMedications(
            name, dosage, frequency, startDate, endDate, medicationTimes, type
        )

        assertEquals(3, medications.size)
        medications.forEach {
            assertEquals(name, it.name)
            assertEquals(dosage, it.dosage)
            assertEquals("Cada 1 día(s)", it.frequency)
            assertEquals(type, it.type)
            assertFalse(it.medicationTaken)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `createMedications throws for invalid frequency`() {
        val now = Date()
        viewModel.createMedications(
            "Test", 1, "INVALID", now, now, emptyList(), MedicationType.TABLET
        )
    }

    @Test
    fun `logEvent calls analyticsHelper`() {
        val eventName = "test_event"
        viewModel.logEvent(eventName)
        verify(analyticsHelper).logEvent(eventName)
    }

    @Test
    fun `createMedications returns empty list when duration is zero or negative`() {
        val name = "Test"
        val dosage = 100
        val frequency = Frequency.EVERYDAY.name
        val now = Date()
        val medicationTimes = listOf(
            CalendarInformation(
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 8)
                    set(Calendar.MINUTE, 0)
                }
            )
        )
        val type = MedicationType.TABLET

        `when`(
            context.getString(Frequency.EVERYDAY.stringResId, Frequency.EVERYDAY.days)
        ).thenReturn("Cada 1 día(s)")

        // startDate > endDate
        val medications = viewModel.createMedications(
            name, dosage, frequency, Date(10000), Date(0), medicationTimes, type
        )
        assertEquals(0, medications.size)
    }

    @Test
    fun `createMedications returns at least one medication when duration less than interval`() {
        val name = "Test"
        val dosage = 100
        val frequency = Frequency.EVERYDAY.name
        val startDate = Date(0)
        val endDate = Date(0) // durationInDays = 1
        val medicationTimes = listOf(
            CalendarInformation(
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 8)
                    set(Calendar.MINUTE, 0)
                }
            )
        )
        val type = MedicationType.TABLET

        `when`(
            context.getString(Frequency.EVERYDAY.stringResId, Frequency.EVERYDAY.days)
        ).thenReturn("Cada 1 día(s)")

        val medications = viewModel.createMedications(
            name, dosage, frequency, startDate, endDate, medicationTimes, type
        )
        assertEquals(1, medications.size)
    }
}
