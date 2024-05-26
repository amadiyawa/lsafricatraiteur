package com.amadiyawa.feature_delivery.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_delivery.presentation.screen.deliverycreate.DeliveryCreateScreen

object DeliveryCreateNavigation: AppNavigationDestination {
    private const val DELIVERY_CREATE = "delivery_create"

    override val route = DELIVERY_CREATE
    override val destination = "delivery_create_destination"
}

fun NavGraphBuilder.deliveryCreateGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = DeliveryCreateNavigation.route
    ) {
        DeliveryCreateScreen(
            onBackClick = onBackClick
        )
    }
}