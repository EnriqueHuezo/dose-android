package com.waseefakhtar.doseapp.feature.home.data

import com.waseefakhtar.doseapp.extension.toFormattedDateString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

class CalendarDataSourceTest {
    private val dataSource = CalendarDataSource()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    @Test
    fun `getLastSelectedDate returns today if parsing fails`() {
        val invalidDate = "not-a-date"
        val result = dataSource.getLastSelectedDate(invalidDate)

        val now = Date()
        assertTrue(abs(result.time - now.time) < 2000)
    }

    @Test
    fun `getLastSelectedDate parses valid date string`() {
        val dateStr = "2024-06-01"
        val result = dataSource.getLastSelectedDate(dateStr)
        assertEquals(dateFormat.parse(dateStr), result)
    }

    @Test
    fun `getData returns week starting on Monday and 7 days`() {
        val startDate = dateFormat.parse("2024-06-05")!! // Wendesday
        val lastSelected = dateFormat.parse("2024-06-06")!!
        val model = dataSource.getData(startDate, lastSelected)
        assertEquals(7, model.visibleDates.size)

        val cal = Calendar.getInstance().apply { time = model.visibleDates.first().date }
        assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK))
    }

    @Test
    fun `selectedDate is marked as selected`() {
        val startDate = dateFormat.parse("2024-06-03")!! // Lunes
        val lastSelected = dateFormat.parse("2024-06-05")!!
        val model = dataSource.getData(startDate, lastSelected)
        assertTrue(model.selectedDate.isSelected)
        assertEquals(lastSelected, model.selectedDate.date)
    }

    @Test
    fun `getData uses today as default startDate`() {
        val lastSelected = dataSource.today
        val model = dataSource.getData(lastSelectedDate = lastSelected)
        // The week should start on Monday and have 7 days
        assertEquals(7, model.visibleDates.size)
        val cal = Calendar.getInstance().apply { time = model.visibleDates.first().date }
        assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK))
    }
}
