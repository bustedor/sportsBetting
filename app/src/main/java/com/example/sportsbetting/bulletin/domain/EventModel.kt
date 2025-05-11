package com.example.sportsbetting.bulletin.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class EventModel(
    val id: String,
    val startTime: Date,
    val homeTeam: String,
    val awayTeam: String
) {
    val formattedStartDate: String
        get() {
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            return formatter.format(startTime)
        }

    val formattedStartTime: String
        get() {
            val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            return formatter.format(startTime)
        }

}

private const val DATE_FORMAT = "dd/MM/yyyy"
private const val TIME_FORMAT = "HH:mm"
