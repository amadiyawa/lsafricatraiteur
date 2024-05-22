package com.amadiyawa.feature_delivery.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_delivery.presentation.screen.deliverylist.DeliveryListScreen

object DeliveryListNavigation: AppNavigationDestination {
    private const val DELIVERY_LIST = "delivery_list"

    override val route: String = DELIVERY_LIST

    override val destination: String = "delivery_list_destination"
}

fun NavGraphBuilder.deliveryListGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = DeliveryListNavigation.route
    ) {
        DeliveryListScreen(
            onBackClick = onBackClick
        )
    }
}