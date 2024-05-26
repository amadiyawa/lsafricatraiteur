package com.amadiyawa.feature_delivery.presentation.screen.deliverylist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amadiyawa.feature_base.common.res.Dimen
import com.amadiyawa.feature_base.presentation.compose.composable.DataNotFoundAnim
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleLarge
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier.padding(Dimen.Spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextTitleLarge(
                text = delivery.customerFullName,
            )
        }
    }
}