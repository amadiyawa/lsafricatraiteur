package com.amadiyawa.lsafricatraiteur.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.ui.graphics.vector.ImageVector
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_contact.presentation.navigation.ContactNavigation
import com.amadiyawa.feature_delivery.presentation.navigation.DeliveryListNavigation
import com.amadiyawa.feature_gallery.presentation.navigation.ImageListNavigation
import com.amadiyawa.feature_service.presentation.navigation.ServiceListNavigation
import com.amadiyawa.lsafricatraiteur.R

sealed class TopLevelDestination(
    override val route: String,
    override val destination: String,
    @StringRes val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    ): AppNavigationDestination {
    data object Service: TopLevelDestination(route = ServiceListNavigation.route, ServiceListNavigation.destination,
        title = R.string.services, selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)

    data object Delivery: TopLevelDestination(route = DeliveryListNavigation.route, DeliveryListNavigation.destination,
        title = R.string.deliveries, selectedIcon = Icons.Filled.DeliveryDining, unselectedIcon = Icons.Outlined.DeliveryDining)

    data object Gallery: TopLevelDestination(route = ImageListNavigation.route, ImageListNavigation.destination,
        title = R.string.gallery, selectedIcon = Icons.Filled.Image, unselectedIcon = Icons.Outlined.Image)

    data object Contact: TopLevelDestination(route = ContactNavigation.route, destination =  ContactNavigation.destination,
        title = R.string.contact, selectedIcon = Icons.Filled.Email, unselectedIcon = Icons.Outlined.Email)
}