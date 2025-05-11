package com.example.sportsbetting.bulletin.data

import com.example.sportsbetting.bulletin.domain.EventModel
import com.example.sportsbetting.util.parseDate
import com.google.gson.annotations.SerializedName

data class EventResponseModel(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("commence_time")
    val startTime: String? = null,
    @SerializedName("home_team")
    val homeTeam: String? = null,
    @SerializedName("away_team")
    val awayTeam: String? = null
)

fun EventResponseModel.toEventModel(): EventModel? {
    if (id.isNullOrBlank()) {
        return null
    }
    val startDate = startTime.parseDate() ?: return null
    return EventModel(
        id = id,
        startTime = startDate,
        homeTeam = homeTeam.orEmpty(),
        awayTeam = awayTeam.orEmpty()
    )
}


