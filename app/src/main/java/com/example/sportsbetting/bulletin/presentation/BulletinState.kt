package com.example.sportsbetting.bulletin.presentation

import com.example.sportsbetting.bulletin.domain.EventModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BulletinState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val events: ImmutableList<EventModel> = persistentListOf()
) {
    val isEmpty: Boolean
        get() = isLoading.not() && errorMessage == null && events.isEmpty()
}
