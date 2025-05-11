package com.example.sportsbetting.bulletin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.sportsbetting.bulletin.presentation.BulletinScreen
import com.example.sportsbetting.bulletin.presentation.BulletinViewModel
import com.example.sportsbetting.navigation.Route

fun NavGraphBuilder.bulletinScreen(
    onDetailNavigation: (String) -> Unit
) {
    composable<Route.Bulletin> {
        val viewModel: BulletinViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        BulletinScreen(
            state = state,
            onDetailNavigation = onDetailNavigation,
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        )
    }
}
