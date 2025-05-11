package com.example.sportsbetting.bulletin.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsbetting.R
import com.example.sportsbetting.bulletin.domain.EventModel
import java.util.Date
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BulletinScreen(
    state: BulletinState,
    onDetailNavigation: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when {
            state.isLoading -> Loading(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
            )

            state.errorMessage != null -> Error(message = state.errorMessage, Modifier.align(Alignment.Center))
            else -> Success(
                events = state.events,
                onEventClick = onDetailNavigation,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

@Composable
private fun Success(
    events: ImmutableList<EventModel>,
    onEventClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Events(
        events = events,
        onEventClick = onEventClick,
        modifier = modifier,
    )
}

@Composable
private fun Events(
    events: ImmutableList<EventModel>,
    onEventClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(events) { index, event ->
            Event(
                event = event,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onEventClick.invoke(event.id) }
                    .fillMaxWidth()
            )
            if (index != events.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun Event(
    event: EventModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        EventHeader(
            startDate = event.formattedStartDate,
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = event.formattedStartTime,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = event.homeTeam, modifier = Modifier.weight(1f))
            Text(text = " - ")
            Text(text = event.awayTeam, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
        }
    }
}

@Composable
private fun EventHeader(
    startDate: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        LeagueTitle(modifier = Modifier.weight(1f))
        Text(
            text = startDate,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End,
            modifier = Modifier
        )
    }
}

@Composable
private fun LeagueTitle(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.ic_flag),
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.turkey_super_league_label),
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Composable
private fun Loading(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
private fun Error(message: String, modifier: Modifier = Modifier) {
    Text(text = message, modifier = modifier, textAlign = TextAlign.Center)
}

@Preview(showBackground = true)
@Composable
private fun BulletinPreview() {
    BulletinScreen(
        state = BulletinState(
            isLoading = false,
            errorMessage = null,
            events = persistentListOf(
                EventModel(
                    id = "1",
                    homeTeam = "Team A",
                    awayTeam = "Team B",
                    startTime = Date()
                ),
                EventModel(
                    id = "1",
                    homeTeam = "Team A",
                    awayTeam = "Team B",
                    startTime = Date()
                )
            )
        ),
        onDetailNavigation = { /* NO-OP */ },
        modifier = Modifier.fillMaxSize()
    )
}
