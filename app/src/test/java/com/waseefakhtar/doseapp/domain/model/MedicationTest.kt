package com.waseefakhtar.doseapp.domain.model

import com.waseefakhtar.doseapp.util.MedicationType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import java.util.Date

class MedicationTest {
    @Test
    fun `Medication initializes with given values`() {
        val start = Date(1000)
        val end = Date(2000)
        val time = Date(1500)
        val medication = Medication(
            id = 1L,
            name = "Ibuprofen",
            dosage = 200,
            frequency = "Once a day",
            startDate = start,
            endDate = end,
            medicationTaken = false,
            medicationTime = time,
            type = MedicationType.TABLET
        )
        assertEquals(1L, medication.id)
        assertEquals("Ibuprofen", medication.name)
        assertEquals(200, medication.dosage)
        assertEquals("Once a day", medication.frequency)
        assertEquals(start, medication.startDate)
        assertEquals(end, medication.endDate)
        assertFalse(medication.medicationTaken)
        assertEquals(time, medication.medicationTime)
        assertEquals(MedicationType.TABLET, medication.type)
    }

    @Test
    fun `Medication uses default id and type`() {
        val now = Date()
        val medication = Medication(
            name = "Paracetamol",
            dosage = 500,
            frequency = "Twice a day",
            startDate = now,
            endDate = now,
            medicationTaken = true,
            medicationTime = now
        )
        assertEquals(0L, medication.id)
        assertEquals(MedicationType.getDefault(), medication.type)
    }

    @Test
    fun `Medication equality and hashCode work correctly`() {
        val now = Date()
        val m1 = Medication(
            name = "A",
            dosage = 1,
            frequency = "f",
            startDate = now,
            endDate = now,
            medicationTaken = true,
            medicationTime = now
        )
        val m2 = m1.copy()
        assertEquals(m1, m2)
        assertEquals(m1.hashCode(), m2.hashCode())
    }

    @Test
    fun `Medication copy creates a new instance with updated values`() {
        val now = Date()
        val original = Medication(
            name = "A",
            dosage = 1,
            frequency = "f",
            startDate = now,
            endDate = now,
            medicationTaken = true,
            medicationTime = now
        )
        val copy = original.copy(name = "B", dosage = 2)
        assertEquals("B", copy.name)
        assertEquals(2, copy.dosage)
        assertEquals(original.frequency, copy.frequency)
    }
}
