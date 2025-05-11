package com.example.sportsbetting.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Bulletin : Route

    @JvmInline
    @Serializable
    value class Detail(val id: String) : Route

    @Serializable
    data object Basket : Route

}
