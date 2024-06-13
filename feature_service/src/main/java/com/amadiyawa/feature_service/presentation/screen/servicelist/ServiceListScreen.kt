package com.amadiyawa.feature_service.presentation.screen.servicelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amadiyawa.feature_base.common.res.Dimen
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleMedium
import com.amadiyawa.feature_service.R
import com.amadiyawa.feature_service.domain.model.Prestation
import com.amadiyawa.feature_service.domain.model.generatePrestationList
import com.amadiyawa.feature_service.presentation.compose.composable.ServiceCarousel
import com.amadiyawa.feature_service.presentation.compose.composable.Toolbar

@Composable
fun ServiceListScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = { SetUpToolbar() },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        SetupContent(
            paddingValues = paddingValues,
        )
    }
}

@Composable
private fun SetUpToolbar() {
    Toolbar(
        centered = false,
        title = stringResource(id = R.string.services),
        onBackClick = {  }
    )
}

@Composable
private fun SetupContent(
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        HandleUiState()
    }
}

@Composable
private fun HandleUiState() {
    val listState = rememberLazyListState()
    val prestationList = generatePrestationList()

    ServiceCarousel(modifier = Modifier.padding(top = 10.dp).height(210.dp))

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        content = {
            items(prestationList.size) { index ->
                PrestationCard(prestation = prestationList[index])
            }
        },
        state = listState,
    )
}

@Composable
private fun PrestationCard(prestation: Prestation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Spacing.large)
        ) {
            TextTitleMedium(
                text = prestation.name,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.medium, Alignment.CenterHorizontally),
            ) {
                Icon(imageVector = Icons.Filled.People, contentDescription = prestation.location)
                TextTitleMedium(
                    text = prestation.guess,
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.medium, Alignment.CenterHorizontally),
            ) {
                Icon(imageVector = Icons.Filled.Money, contentDescription = prestation.location)
                TextTitleMedium(
                    text = prestation.price.toString() + " CFA",
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.medium, Alignment.CenterHorizontally),
            ) {
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = prestation.location)
                TextTitleMedium(
                    text = prestation.location,
                )
            }
        }
    }
}
