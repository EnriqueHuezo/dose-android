package com.waseefakhtar.doseapp.feature.home.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeStateTest {

    @Test
    fun `HomeState initializes with default values except lastSelectedDate`() {
        val state = HomeState(lastSelectedDate = "2024-01-01")
        assertEquals("", state.greeting)
        assertEquals("", state.userName)
        assertEquals("2024-01-01", state.lastSelectedDate)
        assertTrue(state.medications.isEmpty())
    }

    @Test
    fun `HomeState copies correctly with copy`() {
        val original = HomeState(
            greeting = "Hello",
            userName = "User",
            lastSelectedDate = "2024-01-01",
            medications = emptyList()
        )
        val copy = original.copy(userName = "Other")
        assertEquals("Hello", copy.greeting)
        assertEquals("Other", copy.userName)
        assertEquals("2024-01-01", copy.lastSelectedDate)
        assertEquals(original.medications, copy.medications)
    }

    @Test
    fun `HomeState equality and hashCode work correctly`() {
        val state1 = HomeState("Hello", "User", "2024-01-01", emptyList())
        val state2 = HomeState("Hello", "User", "2024-01-01", emptyList())
        assertEquals(state1, state2)
        assertEquals(state1.hashCode(), state2.hashCode())
    }
}
