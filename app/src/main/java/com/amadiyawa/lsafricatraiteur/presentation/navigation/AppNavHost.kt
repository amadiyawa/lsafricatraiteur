package com.amadiyawa.lsafricatraiteur.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_contact.presentation.navigation.contactGraph
import com.amadiyawa.feature_delivery.presentation.navigation.deliveryListGraph
import com.amadiyawa.feature_gallery.presentation.navigation.imageListGraph
import com.amadiyawa.feature_service.presentation.navigation.ServiceListNavigation
import com.amadiyawa.feature_service.presentation.navigation.serviceListGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    onNavigateToDestination: (AppNavigationDestination, String?) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = ServiceListNavigation.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxWidth()
    ) {
        serviceListGraph(
            onBackClick = onBackClick
        )

        deliveryListGraph(
            onBackClick = onBackClick
        )

        imageListGraph(
            onBackClick = onBackClick
        )

        contactGraph(
            onBackClick = onBackClick
        )
    }
}