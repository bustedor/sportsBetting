package com.example.sportsbetting.basket.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sportsbetting.R
import com.example.sportsbetting.basket.domain.BetModel
import com.example.sportsbetting.basket.domain.BetSelection
import com.example.sportsbetting.detail.domain.OddModel
import java.util.Date
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BasketBottomBar(
    state: BasketState,
    onBasketClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .height(64.dp)
            .clickable(onClick = onBasketClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${state.numberOfBets} Bets",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(16.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = stringResource(R.string.total_odds_label),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = state.formattedTotalMultiplier,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, name = "BasketBar - Empty")
@Composable
private fun BasketBarPreview_Empty() {
    MaterialTheme {
        BasketBottomBar(
            state = BasketState(bets = persistentListOf()),
            onBasketClick = {}
        )
    }
}

@Preview(showBackground = true, name = "BasketBar - With Bets")
@Composable
private fun BasketBarPreview_WithBets() {
    MaterialTheme {
        BasketBottomBar(
            state = BasketState(
                bets = persistentListOf(
                    BetModel(
                        oddModel = OddModel(
                            eventId = "1",
                            tournamentTitle = "Premier League",
                            startTime = Date(),
                            homeTeamName = "Team A",
                            awayTeamName = "Team B",
                            bookmaker = "Bookie",
                            homeTeamOdd = 1.5,
                            awayTeamOdd = 2.5,
                            drawOdd = 3.0
                        ),
                        selection = BetSelection.HOME_WIN
                    ),
                    BetModel(
                        oddModel = OddModel(
                            eventId = "2",
                            tournamentTitle = "La Liga",
                            startTime = Date(),
                            homeTeamName = "Team C",
                            awayTeamName = "Team D",
                            bookmaker = "Bookie",
                            homeTeamOdd = 2.0,
                            awayTeamOdd = 2.0,
                            drawOdd = 3.5
                        ),
                        selection = BetSelection.AWAY_WIN
                    )
                )
            ),
            onBasketClick = {}
        )
    }
}
