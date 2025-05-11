package com.example.sportsbetting.basket.presentation

import com.example.sportsbetting.basket.domain.BetModel

sealed interface BasketEvent {

    @JvmInline
    value class BetClick(val bet: BetModel): BasketEvent

}
