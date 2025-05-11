package com.example.sportsbetting.bulletin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsbetting.bulletin.domain.BulletinRepository
import com.example.sportsbetting.util.Result.Companion.onError
import com.example.sportsbetting.util.Result.Companion.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BulletinViewModel @Inject constructor(
    private val repository: BulletinRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BulletinState())
    val state: StateFlow<BulletinState> = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            repository.getData()
                .onSuccess { sports ->
                    _state.update {
                        it.copy(isLoading = false, events = sports.toImmutableList())
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
