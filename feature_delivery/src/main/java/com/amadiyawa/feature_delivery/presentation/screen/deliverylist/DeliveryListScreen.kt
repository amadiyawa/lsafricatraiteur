package com.amadiyawa.feature_delivery.presentation.screen.deliverylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amadiyawa.feature_base.common.res.Dimen
import com.amadiyawa.feature_base.common.util.formatDate
import com.amadiyawa.feature_base.common.util.formatTime
import com.amadiyawa.feature_base.presentation.compose.composable.DataNotFoundAnim
import com.amadiyawa.feature_base.presentation.compose.composable.DrawHorizontalDottedLine
import com.amadiyawa.feature_base.presentation.compose.composable.ExpandableRow
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleMedium
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleSmall
import com.amadiyawa.feature_delivery.R
import com.amadiyawa.feature_delivery.domain.model.Delivery
import com.amadiyawa.feature_delivery.presentation.compose.composable.FloatingActionButton
import com.amadiyawa.feature_delivery.presentation.compose.composable.Toolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DeliveryListScreen(
    onNewDelivery: () -> Unit
) {
    val viewModel: DeliveryListViewModel = koinViewModel()
    viewModel.onEnter()
    val listState = rememberLazyListState()

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        floatingActionButton = {
            FloatingActionButton(
                imageVector = Icons.Filled.Add,
                onClick = { onNewDelivery() },
                isVisible = true,
                label = stringResource(id = R.string.new_delivery)
            )
        },
        topBar = { SetUpToolbar() },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        SetupContent(
            paddingValues = paddingValues,
            viewModel = viewModel,
            listState = listState,
        )
    }
}

@Composable
private fun SetUpToolbar() {
    Toolbar(
        centered = false,
        title = stringResource(id = R.string.deliveries),
        onBackClick = {  }
    )
}

@Composable
private fun SetupContent(
    paddingValues: PaddingValues,
    viewModel: DeliveryListViewModel,
    listState: LazyListState,
) {
    val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        HandleUiState(
            uiState = uiState,
            listState = listState
        )
    }
}

@Composable
private fun HandleUiState(
    uiState: State<DeliveryListViewModel.UiState>,
    listState: LazyListState,
) {
    when (uiState.value) {
        is DeliveryListViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(visible = true)
            }
        }
        is DeliveryListViewModel.UiState.Empty -> {
            DataNotFoundAnim()
        }
        is DeliveryListViewModel.UiState.Content -> {
            val deliveryList = (uiState.value as DeliveryListViewModel.UiState.Content).deliveryList
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                content = {
                    items(deliveryList.size) { index ->
                        DeliveryCard(
                            delivery = deliveryList[index],
                        )
                    }
                },
                state = listState,
            )
        }
        is DeliveryListViewModel.UiState.Error -> {
            DataNotFoundAnim()
        }
    }
}

@Composable
private fun DeliveryCard(
    delivery: Delivery
) {
    val expandedMenus = remember { mutableStateOf (false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DeliveryCardHeader(delivery = delivery)
            DrawHorizontalDottedLine(
                color = MaterialTheme.colorScheme.onSurface,
                startPadding = 0.dp,
                endPadding = 0.dp
            )
            DeliveryCardContent(delivery = delivery)
            HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.background)
            ExpandableRow(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                expanded = expandedMenus.value,
                fixedLabel = stringResource(id = R.string.menus),
                onRowClick = {
                    expandedMenus.value = !expandedMenus.value
                }
            )
            AnimatedVisibility(visible = expandedMenus.value) {
                DeliveryMenus(delivery = delivery)
            }
        }
    }
}

@Composable
private fun DeliveryCardHeader(
    delivery: Delivery
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.Spacing.large),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            TextTitleMedium(
                text = delivery.customerFullName,
                fontWeight = FontWeight.Bold
            )

            TextTitleSmall(text = delivery.paymentPhoneNumber)
        }

        Spacer(modifier = Modifier.weight(1f))

        Badge(
            modifier = Modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            content = {
                Row(
                    modifier = Modifier.padding(Dimen.Spacing.medium),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    TextTitleSmall(
                        text = delivery.status,
                    )
                }
            }
        )
    }
}

@Composable
private fun DeliveryCardContent(
    delivery: Delivery
) {
    val deliveryContent = delivery.address + ", " + formatDate(delivery.deliveryDate) + " " + stringResource(
        id = R.string.at
    ) + " " + formatTime(delivery.deliveryDate)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.Spacing.large),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimen.Spacing.medium, Alignment.CenterHorizontally),
        ) {
            Icon(imageVector = Icons.Filled.DeliveryDining, contentDescription = deliveryContent)
            TextTitleMedium(
                text = deliveryContent,
            )
        }
    }
}

@Composable
private fun DeliveryMenus(
    delivery: Delivery
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.Spacing.large),
        verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.medium, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        delivery.menuList.forEach {
            TextTitleSmall(
                text = it,
            )
        }
    }
}