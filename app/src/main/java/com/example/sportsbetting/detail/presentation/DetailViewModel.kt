package com.example.sportsbetting.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.sportsbetting.util.Result.Companion.onError
import com.example.sportsbetting.util.Result.Companion.onSuccess
import com.example.sportsbetting.detail.domain.DetailRepository
import com.example.sportsbetting.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val repository: DetailRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    init {
        val route = stateHandle.toRoute<Route.Detail>()
        fetchData(eventId = route.id)
    }

    private fun fetchData(eventId: String) {
        viewModelScope.launch {
            repository.getOdds(eventId = eventId)
                .onSuccess { odds ->
                    _state.update {
                        it.copy(isLoading = false, odds = odds.toImmutableList())
                    }
                }
                .onError { errorMessage ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = errorMessage)
                    }
                }
        }
    }

}
