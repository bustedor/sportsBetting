package com.example.sportsbetting.detail.data

import com.example.sportsbetting.detail.domain.OddModel
import com.example.sportsbetting.util.parseDate
import com.google.gson.annotations.SerializedName

private const val NAME_DRAW = "Draw"

data class OddsResponseModel(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("sport_key")
    val sportKey: String? = null,
    @SerializedName("sport_title")
    val sportTitle: String? = null,
    @SerializedName("commence_time")
    val commenceTime: String? = null,
    @SerializedName("home_team")
    val homeTeam: String? = null,
    @SerializedName("away_team")
    val awayTeam: String? = null,
    @SerializedName("bookmakers")
    val bookmakers: List<Bookmaker>? = null
)

data class Bookmaker(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("markets")
    val markets: List<Market>? = null
)

data class Market(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("last_update")
    val lastUpdate: String? = null,
    @SerializedName("outcomes")
    val outcomes: List<Outcome>? = null
)

data class Outcome(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("price")
    val price: Double? = null
)

fun OddsResponseModel.toOddModels(): List<OddModel> {
    val homeTeam = homeTeam.orEmpty()
    val awayTeam = awayTeam.orEmpty()
    if (homeTeam.isBlank() || awayTeam.isBlank()) {
        return emptyList()
    }

    return bookmakers.orEmpty().mapNotNull { bookmaker ->
        val title = bookmaker.title.orEmpty()
        if (title.isBlank()) return@mapNotNull null
        val startDate = commenceTime.parseDate() ?: return@mapNotNull null

        val outcomes = bookmaker.markets?.firstOrNull()?.outcomes.orEmpty()

        val homeOutcome = outcomes.find { it.name == homeTeam && it.price.orZero() > 0 }
        val awayOutcome = outcomes.find { it.name == awayTeam && it.price.orZero() > 0 }
        val drawOutcome = outcomes.find { it.name == NAME_DRAW && it.price.orZero() > 0 }

        if (homeOutcome != null && awayOutcome != null && drawOutcome != null) {
            OddModel(
                eventId = id.orEmpty(),
                tournamentTitle = sportTitle.orEmpty(),
                startTime = startDate,
                homeTeamName = homeTeam,
                awayTeamName = awayTeam,
                bookmaker = title,
                homeTeamOdd = homeOutcome.price ?: 0.0,
                awayTeamOdd = awayOutcome.price ?: 0.0,
                drawOdd = drawOutcome.price ?: 0.0
            )
        } else {
            null
        }
    }
}

private fun Double?.orZero(): Double = this ?: 0.0
