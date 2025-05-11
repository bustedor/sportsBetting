package com.example.sportsbetting.basket.domain

import com.example.sportsbetting.detail.domain.OddModel
import java.text.DecimalFormat

enum class BetSelection(val displayName: String) {
    HOME_WIN(displayName = "Home Win"),
    AWAY_WIN(displayName = "Away Win"),
    DRAW(displayName = "Draw")
}

data class BetModel(
    val oddModel: OddModel,
    val selection: BetSelection
) {
    val selectedOddValue: Double
        get() = when (selection) {
            BetSelection.HOME_WIN -> oddModel.homeTeamOdd
            BetSelection.AWAY_WIN -> oddModel.awayTeamOdd
            BetSelection.DRAW -> oddModel.drawOdd
        }

    val formattedSelectedOddValue: String
        get() = DecimalFormat("0.00").format(selectedOddValue)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BetModel) return false

        return oddModel.eventId == other.oddModel.eventId &&
            oddModel.bookmaker == other.oddModel.bookmaker &&
            selection == other.selection
    }

    override fun hashCode(): Int {
        var result = oddModel.eventId.hashCode()
        result = 31 * result + oddModel.bookmaker.hashCode()
        result = 31 * result + selection.hashCode()
        return result
    }

}
