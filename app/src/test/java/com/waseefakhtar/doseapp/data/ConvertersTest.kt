package com.waseefakhtar.doseapp.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.util.Date

class ConvertersTest {
    private lateinit var converters: Converters

    @Before
    fun setUp() {
        converters = Converters()
    }

    @Test
    fun `fromTimestamp returns null when value is null`() {
        val result = converters.fromTimestamp(null)
        assertNull(result)
    }

    @Test
    fun `fromTimestamp returns Date when value is not null`() {
        val timestamp = 1680000000000L
        val result = converters.fromTimestamp(timestamp)
        assertNotNull(result)
        assertEquals(Date(timestamp), result)
    }

    @Test
    fun `dateToTimestamp returns null when date is null`() {
        val result = converters.dateToTimestamp(null)
        assertNull(result)
    }

    @Test
    fun `dateToTimestamp returns timestamp when date is not null`() {
        val date = Date(1680000000000L)
        val result = converters.dateToTimestamp(date)
        assertNotNull(result)
        assertEquals(date.time, result)
    }
}
