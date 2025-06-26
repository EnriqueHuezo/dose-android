package com.waseefakhtar.doseapp.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SnackbarUtilTest {
    @Before
    fun setUp() {
        SnackbarUtil.hideSnackbar()
        SnackbarUtil.showSnackbar("")
    }

    @Test
    fun `showSnackbar sets message and visibility`() {
        val testMessage = "Test message"
        SnackbarUtil.showSnackbar(testMessage)
        assertEquals(testMessage, SnackbarUtil.getSnackbarMessage().value)
        assertTrue(SnackbarUtil.isSnackbarVisible().value)
    }

    @Test
    fun `hideSnackbar sets visibility to false`() {
        SnackbarUtil.showSnackbar("Another message")
        SnackbarUtil.hideSnackbar()
        assertFalse(SnackbarUtil.isSnackbarVisible().value)
    }

    @Test
    fun `getSnackbarMessage returns correct value`() {
        val msg = "Snackbar message"
        SnackbarUtil.showSnackbar(msg)
        assertEquals(msg, SnackbarUtil.getSnackbarMessage().value)
    }
}
