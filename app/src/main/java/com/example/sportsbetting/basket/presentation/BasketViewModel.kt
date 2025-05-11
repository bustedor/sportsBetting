package com.example.sportsbetting.basket.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.sportsbetting.basket.domain.BetModel
import com.example.sportsbetting.basket.domain.BetSelection
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    private val _state = MutableStateFlow(BasketState())
    val state: StateFlow<BasketState> = _state

    fun onEvent(event: BasketEvent) {
        when (event) {
            is BasketEvent.BetClick -> onPlaceBet(bet = event.bet)
        }
    }

    private fun onPlaceBet(bet: BetModel) {
        val existingBet = _state.value.bets.find { it.oddModel == bet.oddModel }
        when {
            existingBet != null && existingBet.selection == bet.selection -> removeBet(bet = bet)
            existingBet != null -> updateBet(bet = existingBet, selection = bet.selection)
            else -> addBet(bet = bet)

        }
    }

    private fun removeBet(bet: BetModel) {
        _state.update { state ->
            state.copy(
                bets = state.bets.filterNot { it == bet }.toImmutableList()
            )
        }
        val params = Bundle().apply {
            putString("event_id", bet.oddModel.eventId)
            putString("bookmaker", bet.oddModel.bookmaker)
            putString("selection", bet.selection.displayName)
        }
        firebaseAnalytics.logEvent("remove_bet", params)
    }

    private fun updateBet(bet: BetModel, selection: BetSelection) {
        _state.update { state ->
            state.copy(
                bets = state.bets.map {
                    if (it == bet) {
                        bet.copy(selection = selection)
                    } else {
                        it
                    }
                }.toImmutableList()
            )
        }
        val params = Bundle().apply {
            putString("event_id", bet.oddModel.eventId)
            putString("bookmaker", bet.oddModel.bookmaker)
            putString("selection", bet.selection.displayName)
        }
        firebaseAnalytics.logEvent("update_bet", params)
    }

    private fun addBet(bet: BetModel) {
        _state.update { state ->
            state.copy(
                bets = (state.bets + bet).toImmutableList()
            )
        }
        val params = Bundle().apply {
            putString("event_id", bet.oddModel.eventId)
            putString("bookmaker", bet.oddModel.bookmaker)
            putString("selection", bet.selection.displayName)
        }
        firebaseAnalytics.logEvent("add_bet", params)
    }

}
