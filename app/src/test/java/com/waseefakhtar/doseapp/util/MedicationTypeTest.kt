package com.waseefakhtar.doseapp.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MedicationTypeTest {
    @Test
    fun `getDefault should return TABLET`() {
        val defaultType = MedicationType.getDefault()
        assertEquals(MedicationType.TABLET, defaultType)
    }

    @Test
    fun `getMedicationTypes should return all medication types`() {
        val types = getMedicationTypes()
        assertEquals(MedicationType.entries.size, types.size)
        assertTrue(types.contains(MedicationType.TABLET))
        assertTrue(types.contains(MedicationType.CAPSULE))
        assertTrue(types.contains(MedicationType.SYRUP))
        assertTrue(types.contains(MedicationType.DROPS))
        assertTrue(types.contains(MedicationType.SPRAY))
        assertTrue(types.contains(MedicationType.GEL))
    }
}
