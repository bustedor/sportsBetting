package com.example.sportsbetting.basket.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sportsbetting.R
import com.example.sportsbetting.basket.domain.BetModel
import com.example.sportsbetting.basket.domain.BetSelection
import com.example.sportsbetting.detail.domain.OddModel
import java.util.Date
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BasketScreen(
    state: BasketState,
    onEvent: (BasketEvent) -> Unit,
    onBulletinNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Content(
            state = state,
            onEvent = onEvent,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Button(onClick = onBulletinNavigation) {
            Text(
                text = stringResource(R.string.bulletin_label),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        }
    }
}

@Composable
private fun Content(
    state: BasketState,
    onEvent: (BasketEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (state.isEmpty) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.empty_basket_label),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Column(modifier = Modifier.matchParentSize()) {
                Text(
                    text = stringResource(R.string.basket_screen_title),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(state.bets) { betModel ->
                        BetItemCard(
                            betModel = betModel,
                            onRemoveBet = {
                                onEvent.invoke(BasketEvent.BetClick(it))
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun BetItemCard(
    betModel: BetModel,
    onRemoveBet: (BetModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${betModel.oddModel.homeTeamName} vs ${betModel.oddModel.awayTeamName}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Bookmaker: ${betModel.oddModel.bookmaker}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your pick: ${betModel.selection.displayName} @ ${betModel.formattedSelectedOddValue}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { onRemoveBet.invoke(betModel) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove bet"
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "BasketScreen - Empty")
@Composable
private fun BasketScreenPreview_Empty() {
    MaterialTheme {
        BasketScreen(
            state = BasketState(bets = persistentListOf()),
            onBulletinNavigation = {},
            onEvent = {},
        )
    }
}

@Preview(showBackground = true, name = "BasketScreen - With Bets")
@Composable
private fun BasketScreenPreview_WithBets() {
    MaterialTheme {
        BasketScreen(
            state = BasketState(
                bets = persistentListOf(
                    BetModel(
                        oddModel = OddModel(
                            "1",
                            "Premier League Football",
                            Date(),
                            "Manchester United FC",
                            "Liverpool Football Club",
                            "BookieAlphaBest",
                            1.75,
                            3.20,
                            2.50
                        ),
                        selection = BetSelection.HOME_WIN
                    ),
                    BetModel(
                        oddModel = OddModel(
                            "2",
                            "La Liga Championship",
                            Date(),
                            "Real Madrid CF",
                            "FC Barcelona",
                            "BookieBetaWin",
                            2.0,
                            3.0,
                            2.20
                        ),
                        selection = BetSelection.DRAW
                    )
                )
            ),
            onBulletinNavigation = {},
            onEvent = {},
        )
    }
}
