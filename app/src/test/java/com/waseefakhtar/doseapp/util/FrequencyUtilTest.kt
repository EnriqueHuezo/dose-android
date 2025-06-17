package com.waseefakhtar.doseapp.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FrequencyUtilTest {
    @Test
    fun `fromDays returns the correct enum for each number of days`() {
        assertEquals(Frequency.EVERYDAY, Frequency.fromDays(1))
        assertEquals(Frequency.EVERY_2_DAYS, Frequency.fromDays(2))
        assertEquals(Frequency.EVERY_3_DAYS, Frequency.fromDays(3))
        assertEquals(Frequency.EVERY_4_DAYS, Frequency.fromDays(4))
        assertEquals(Frequency.EVERY_5_DAYS, Frequency.fromDays(5))
        assertEquals(Frequency.EVERY_6_DAYS, Frequency.fromDays(6))
        assertEquals(Frequency.EVERY_WEEK, Frequency.fromDays(7))
        assertEquals(Frequency.EVERY_2_WEEKS, Frequency.fromDays(14))
        assertEquals(Frequency.EVERY_3_WEEKS, Frequency.fromDays(21))
        assertEquals(Frequency.EVERY_MONTH, Frequency.fromDays(30))
    }

    @Test
    fun `fromDays returns EVERYDAY for undefined values`() {
        assertEquals(Frequency.EVERYDAY, Frequency.fromDays(0))
        assertEquals(Frequency.EVERYDAY, Frequency.fromDays(100))
        assertEquals(Frequency.EVERYDAY, Frequency.fromDays(-6))
    }

    @Test
    fun `getFrequencyList returns all enum values in order`() {
        val expected = listOf(
            Frequency.EVERYDAY,
            Frequency.EVERY_2_DAYS,
            Frequency.EVERY_3_DAYS,
            Frequency.EVERY_4_DAYS,
            Frequency.EVERY_5_DAYS,
            Frequency.EVERY_6_DAYS,
            Frequency.EVERY_WEEK,
            Frequency.EVERY_2_WEEKS,
            Frequency.EVERY_3_WEEKS,
            Frequency.EVERY_MONTH
        )
        val actual = getFrequencyList()
        assertEquals(expected, actual)
    }

    @Test
    fun `all enum values are present in getFrequencyList`() {
        val allEnumValues = Frequency.entries.toSet()
        val listValues = getFrequencyList().toSet()
        assertTrue(listValues.containsAll(allEnumValues))
    }
}
