package com.amadiyawa.feature_service.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_service.presentation.screen.servicelist.ServiceListScreen

object ServiceListNavigation: AppNavigationDestination {
    private const val SERVICE_LIST = "service_list"

    override val route: String = SERVICE_LIST

    override val destination: String = "service_list_destination"
}

fun NavGraphBuilder.serviceListGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = ServiceListNavigation.route
    ) {
        ServiceListScreen(
            onBackClick = onBackClick
        )
    }
}