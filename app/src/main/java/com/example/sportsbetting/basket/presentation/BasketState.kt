package com.example.sportsbetting.basket.presentation

import com.example.sportsbetting.basket.domain.BetModel
import java.text.DecimalFormat
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BasketState(
    val bets: ImmutableList<BetModel> = persistentListOf()
) {
    val numberOfBets: Int
        get() = bets.size

    val totalMultiplier: Double
        get() = if (bets.isEmpty()) {
            0.0
        } else {
            bets.fold(1.0) { acc, betItem -> acc * betItem.selectedOddValue }
        }

    val formattedTotalMultiplier: String
        get() = DecimalFormat("0.00").format(totalMultiplier)

    val isEmpty: Boolean
        get() = bets.isEmpty()

}
