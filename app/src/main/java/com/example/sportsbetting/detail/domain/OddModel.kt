package com.example.sportsbetting.detail.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class OddModel(
    val eventId: String,
    val tournamentTitle: String,
    val startTime: Date,
    val homeTeamName: String,
    val awayTeamName: String,
    val bookmaker: String,
    val homeTeamOdd: Double,
    val awayTeamOdd: Double,
    val drawOdd: Double
) {
    val formattedStartTime: String
        get() {
            val formatter = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
            return formatter.format(startTime)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OddModel) return false

        return eventId == other.eventId && bookmaker == other.bookmaker
    }

    override fun hashCode(): Int {
        var result = eventId.hashCode()
        result = 31 * result + bookmaker.hashCode()
        return result
    }
}
