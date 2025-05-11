package com.example.sportsbetting.detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sportsbetting.R
import com.example.sportsbetting.basket.domain.BetModel
import com.example.sportsbetting.basket.domain.BetSelection
import com.example.sportsbetting.basket.presentation.BasketState
import com.example.sportsbetting.detail.domain.OddModel
import java.util.Date
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DetailScreen(
    state: DetailState,
    basketState: BasketState,
    onPlaceBet: (bet: BetModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> Loading(
                modifier = Modifier
                    .align(Alignment.Center)
            )

            state.errorMessage != null -> Error(
                message = state.errorMessage,
                modifier = Modifier.align(Alignment.Center)
            )

            else -> Success(
                odds = state.odds,
                placedBets = basketState.bets,
                onPlaceBet = onPlaceBet,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

@Composable
private fun MatchHeader(
    tournamentTitle: String,
    startTime: String,
    homeTeamName: String,
    awayTeamName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = tournamentTitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "$homeTeamName vs $awayTeamName",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = startTime,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun Success(
    odds: ImmutableList<OddModel>,
    placedBets: ImmutableList<BetModel>,
    onPlaceBet: (bet: BetModel) -> Unit,
    modifier: Modifier = Modifier
) {
    if (odds.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.no_odds_for_event))
        }
        return
    }

    val commonEventData = odds.first()

    LazyColumn(modifier = modifier) {
        stickyHeader {
            MatchHeader(
                tournamentTitle = commonEventData.tournamentTitle,
                startTime = commonEventData.formattedStartTime,
                homeTeamName = commonEventData.homeTeamName,
                awayTeamName = commonEventData.awayTeamName
            )
        }

        itemsIndexed(odds) { index, oddModel ->
            OddBookmakerItem(
                oddModel = oddModel,
                placedBets = placedBets,
                onPlaceBet = onPlaceBet,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun OddDisplay(
    isSelected: Boolean,
    label: String,
    oddValue: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .then(
                if (isSelected) {
                    Modifier.background(Color.Green)
                } else {
                    Modifier
                }
            )
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = oddValue,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun OddBookmakerItem(
    oddModel: OddModel,
    placedBets: ImmutableList<BetModel>,
    onPlaceBet: (bet: BetModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val placedBet: BetModel? =
        placedBets.find { it.oddModel.bookmaker == oddModel.bookmaker && it.oddModel.eventId == oddModel.eventId }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = oddModel.bookmaker,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OddDisplay(
                    isSelected = placedBet != null && placedBet.selection == BetSelection.HOME_WIN,
                    label = oddModel.homeTeamName,
                    oddValue = oddModel.homeTeamOdd.toString(),
                    modifier = Modifier
                        .weight(3f)
                        .clickable {
                            onPlaceBet.invoke(
                                BetModel(
                                    oddModel = oddModel,
                                    selection = BetSelection.HOME_WIN
                                )
                            )
                        }

                )
                Spacer(modifier = Modifier.width(8.dp))
                OddDisplay(
                    isSelected = placedBet != null && placedBet.selection == BetSelection.DRAW,
                    label = "Draw",
                    oddValue = oddModel.drawOdd.toString(),
                    modifier = Modifier
                        .weight(2f)
                        .clickable {
                            onPlaceBet.invoke(
                                BetModel(
                                    oddModel = oddModel,
                                    selection = BetSelection.DRAW
                                )
                            )
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                OddDisplay(
                    isSelected = placedBet != null && placedBet.selection == BetSelection.AWAY_WIN,
                    label = oddModel.awayTeamName,
                    oddValue = oddModel.awayTeamOdd.toString(),
                    modifier = Modifier
                        .weight(3f)
                        .clickable {
                            onPlaceBet.invoke(
                                BetModel(
                                    oddModel = oddModel,
                                    selection = BetSelection.AWAY_WIN
                                )
                            )
                        }
                )
            }
        }
    }
}

@Composable
private fun Loading(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier.size(48.dp))
}

@Composable
private fun Error(message: String, modifier: Modifier = Modifier) {
    Text(
        text = message,
        modifier = modifier.padding(16.dp),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.error
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DetailPreview() {
    MaterialTheme {
        DetailScreen(
            state = DetailState(
                isLoading = false,
                odds = persistentListOf(
                    OddModel(
                        eventId = "1",
                        tournamentTitle = "Premier League",
                        startTime = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24),
                        homeTeamName = "Manchester United",
                        awayTeamName = "Liverpool FC",
                        bookmaker = "Bookmaker Alpha",
                        homeTeamOdd = 1.75,
                        awayTeamOdd = 2.50,
                        drawOdd = 3.20
                    ),
                    OddModel(
                        eventId = "1",
                        tournamentTitle = "Premier League",
                        startTime = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24),
                        homeTeamName = "Manchester United",
                        awayTeamName = "Liverpool FC",
                        bookmaker = "Best Odds Inc.",
                        homeTeamOdd = 1.80,
                        awayTeamOdd = 2.45,
                        drawOdd = 3.15
                    ),
                    OddModel(
                        eventId = "1",
                        tournamentTitle = "Premier League",
                        startTime = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24),
                        homeTeamName = "Manchester United",
                        awayTeamName = "Liverpool FC",
                        bookmaker = "SureBet Central",
                        homeTeamOdd = 1.70,
                        awayTeamOdd = 2.55,
                        drawOdd = 3.30
                    )
                )
            ),
            basketState = BasketState(
                bets = persistentListOf(
                    BetModel(
                        oddModel = OddModel(
                            "1",
                            "Premier League",
                            Date(),
                            "Manchester United",
                            "Liverpool FC",
                            "Bookmaker Alpha",
                            1.75,
                            2.50,
                            3.20
                        ),
                        selection = BetSelection.HOME_WIN
                    )
                )
            ),
            onPlaceBet = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DetailPreview_Empty() {
    MaterialTheme {
        DetailScreen(
            state = DetailState(
                isLoading = false,
                odds = persistentListOf(),
                errorMessage = null
            ),
            basketState = BasketState(),
            onPlaceBet = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DetailPreview_Loading() {
    MaterialTheme {
        DetailScreen(
            state = DetailState(
                isLoading = true
            ),
            basketState = BasketState(),
            onPlaceBet = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun DetailPreview_Error() {
    MaterialTheme {
        DetailScreen(
            state = DetailState(
                isLoading = false,
                errorMessage = "Could not load match details. Please try again."
            ),
            basketState = BasketState(),
            onPlaceBet = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
