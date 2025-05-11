package com.example.sportsbetting.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val TIME_ZONE = "UTC"

fun String?.parseDate(): Date? {
    val dateText = this ?: return null
    return runCatching {
        val format = SimpleDateFormat(DATE_FORMAT, Locale.US)
        format.timeZone = TimeZone.getTimeZone(TIME_ZONE)
        format.parse(dateText)
    }.getOrNull()
}
