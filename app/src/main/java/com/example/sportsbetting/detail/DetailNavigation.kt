package com.example.sportsbetting.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.sportsbetting.basket.presentation.BasketEvent
import com.example.sportsbetting.basket.presentation.BasketViewModel
import com.example.sportsbetting.detail.presentation.DetailScreen
import com.example.sportsbetting.detail.presentation.DetailViewModel
import com.example.sportsbetting.navigation.Route

fun NavGraphBuilder.detailScreen() {
    composable<Route.Detail> { backStackEntry ->
        val viewModel: DetailViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        val basketViewModel: BasketViewModel = hiltViewModel(LocalContext.current as ViewModelStoreOwner)
        val basketState by basketViewModel.state.collectAsStateWithLifecycle()

        DetailScreen(
            state = state,
            basketState = basketState,
            onPlaceBet = { basketViewModel.onEvent(BasketEvent.BetClick(it)) },
            modifier =
                Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
        )
    }
}
