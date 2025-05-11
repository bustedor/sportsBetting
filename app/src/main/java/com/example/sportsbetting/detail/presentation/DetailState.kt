package com.example.sportsbetting.detail.presentation

import com.example.sportsbetting.detail.domain.OddModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class DetailState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val odds: ImmutableList<OddModel> = persistentListOf()
) {
    val isEmpty: Boolean
        get() = isLoading.not() && errorMessage == null && odds.isEmpty()
}
