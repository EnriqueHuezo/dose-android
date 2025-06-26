package com.waseefakhtar.doseapp.extension

import com.waseefakhtar.doseapp.R
import org.junit.Assert.assertEquals
import org.junit.Test

class DateExtensionsTest {
    @Test
    fun `DurationType enum has correct plural resource ids`() {
        assertEquals(R.plurals.duration_days, DurationType.DAYS.pluralResId)
        assertEquals(R.plurals.duration_weeks, DurationType.WEEKS.pluralResId)
        assertEquals(R.plurals.duration_months, DurationType.MONTHS.pluralResId)
        assertEquals(R.plurals.duration_years, DurationType.YEARS.pluralResId)
    }

    @Test
    fun `Duration primary only constructor sets values correctly`() {
        val duration = Duration(
            primary = 5,
            primaryType = DurationType.DAYS
        )
        assertEquals(5, duration.primary)
        assertEquals(DurationType.DAYS, duration.primaryType)
        assertEquals(null, duration.remainder)
        assertEquals(null, duration.remainderType)
    }

    @Test
    fun `Duration with remainder sets all values correctly`() {
        val duration = Duration(
            primary = 2,
            primaryType = DurationType.YEARS,
            remainder = 3,
            remainderType = DurationType.MONTHS
        )
        assertEquals(2, duration.primary)
        assertEquals(DurationType.YEARS, duration.primaryType)
        assertEquals(3, duration.remainder)
        assertEquals(DurationType.MONTHS, duration.remainderType)
    }
}
