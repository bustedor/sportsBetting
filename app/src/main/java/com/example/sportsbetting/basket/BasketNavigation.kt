package com.example.sportsbetting.basket

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.sportsbetting.basket.presentation.BasketScreen
import com.example.sportsbetting.basket.presentation.BasketViewModel
import com.example.sportsbetting.navigation.Route

fun NavGraphBuilder.basketScreen(
    onBulletinNavigation: () -> Unit
) {
    composable<Route.Basket> {
        val viewModel: BasketViewModel = hiltViewModel(LocalContext.current as ViewModelStoreOwner)
        val state by viewModel.state.collectAsStateWithLifecycle()

        BasketScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onBulletinNavigation = onBulletinNavigation,
            modifier = Modifier.fillMaxSize()
        )
    }
}
