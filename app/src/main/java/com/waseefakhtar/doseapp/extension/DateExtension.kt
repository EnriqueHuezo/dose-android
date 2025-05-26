package com.waseefakhtar.doseapp.extension

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.toFormattedDateString(): String {
    val sdf = SimpleDateFormat("EEEE, LLLL dd", Locale.getDefault())
    return sdf.format(this)
}

fun Long.epochToLocalTimeZoneConvertor(): Long {
    val epochCalendar = Calendar.getInstance()
    epochCalendar.timeZone = TimeZone.getTimeZone("UTC")
    epochCalendar.timeInMillis = this
    val converterCalendar = Calendar.getInstance()
    converterCalendar.set(
        epochCalendar.get(Calendar.YEAR),
        epochCalendar.get(Calendar.MONTH),
        epochCalendar.get(Calendar.DATE),
        epochCalendar.get(Calendar.HOUR_OF_DAY),
        epochCalendar.get(Calendar.MINUTE),
    )
    converterCalendar.timeZone = TimeZone.getDefault()
    return converterCalendar.timeInMillis
}

fun Date.toFormattedMonthDateString(): String {
    val sdf = SimpleDateFormat("MMMM dd", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toFormattedYearMonthDateString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}

fun String.toDate(): Date? {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Date.toFormattedDateShortString(): String {
    val sdf = SimpleDateFormat("dd", Locale.getDefault())
    return sdf.format(this)
}

fun Long.toFormattedDateString(): String {
    val sdf = SimpleDateFormat("LLLL dd, yyyy", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toFormattedTimeString(): String {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(this)
}

fun Date.hasPassed(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.SECOND, -1)
    val oneSecondAgo = calendar.time
    return time < oneSecondAgo.time
}
