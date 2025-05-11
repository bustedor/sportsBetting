package com.example.sportsbetting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.sportsbetting.basket.basketScreen
import com.example.sportsbetting.basket.presentation.BasketBottomBar
import com.example.sportsbetting.basket.presentation.BasketViewModel
import com.example.sportsbetting.bulletin.bulletinScreen
import com.example.sportsbetting.detail.detailScreen
import com.example.sportsbetting.navigation.Route
import com.example.sportsbetting.theme.SportsBettingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val basketViewModel: BasketViewModel = hiltViewModel()
            val basketState by basketViewModel.state.collectAsStateWithLifecycle()

            SportsBettingTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.Bulletin,
                        modifier = Modifier.weight(1f)
                    ) {
                        bulletinScreen(onDetailNavigation = { id -> navController.navigate(Route.Detail(id)) })
                        detailScreen()
                        basketScreen(
                            onBulletinNavigation = {
                                navController.navigate(Route.Bulletin) {
                                    popUpTo<Route.Bulletin> {
                                        inclusive = false
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    BasketBottomBar(
                        state = basketState,
                        onBasketClick = {
                            if (navController.currentDestination?.hasRoute<Route.Basket>() != true) {
                                navController.navigate(Route.Basket)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
