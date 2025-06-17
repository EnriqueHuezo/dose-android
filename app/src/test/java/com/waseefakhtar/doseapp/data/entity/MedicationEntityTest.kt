package com.waseefakhtar.doseapp.data.entity

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class MedicationEntityTest {
    @Test
    fun `medication entity is created with correct values`() {
        val startDate = Date()
        val endDate = Date()
        val medicationTime = Date()

        val entity = MedicationEntity(
            id = 1L,
            name = "Aspirin",
            dosage = 100,
            recurrence = "Daily",
            startDate = startDate,
            endDate = endDate,
            medicationTaken = false,
            medicationTime = medicationTime,
            type = "TABLET"
        )

        assertEquals(1L, entity.id)
        assertEquals("Aspirin", entity.name)
        assertEquals(100, entity.dosage)
        assertEquals("Daily", entity.recurrence)
        assertEquals(startDate, entity.startDate)
        assertEquals(endDate, entity.endDate)
        assertEquals(false, entity.medicationTaken)
        assertEquals(medicationTime, entity.medicationTime)
        assertEquals("TABLET", entity.type)
    }

    @Test
    fun `medication entity uses default type when not specified`() {
        val endDate = Date()
        val medicationTime = Date()

        val entity = MedicationEntity(
            name = "Ibuprofen",
            dosage = 200,
            recurrence = "Weekly",
            startDate = null,
            endDate = endDate,
            medicationTaken = true,
            medicationTime = medicationTime
        )

        assertEquals("TABLET", entity.type)
    }
}
