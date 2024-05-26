package com.amadiyawa.feature_delivery.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_delivery.presentation.screen.deliverylist.DeliveryListScreen

object DeliveryListNavigation: AppNavigationDestination {
    private const val DELIVERY_LIST = "delivery_list"
    private const val DELIVERY_CREATE = "delivery_create"

    fun deliveryCreateRoute() = DELIVERY_CREATE

    override val route: String = DELIVERY_LIST
    override val destination: String = "delivery_list_destination"
}

fun NavGraphBuilder.deliveryListGraph(
    onNewDelivery: () -> Unit,
    onBackClick: () -> Unit
) {
    navigation(
        startDestination = DeliveryListNavigation.destination,
        route = DeliveryListNavigation.route
    ) {
        composable(route = DeliveryListNavigation.destination) {
            DeliveryListScreen(
                onNewDelivery = onNewDelivery
            )
        }
        deliveryCreateGraph(onBackClick = onBackClick)
    }
}