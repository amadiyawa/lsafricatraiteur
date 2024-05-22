package com.amadiyawa.feature_contact.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_contact.presentation.screen.contact.ContactScreen

object ContactNavigation: AppNavigationDestination {
    private const val CONTACT = "contact"

    override val route: String = CONTACT

    override val destination: String = "contact_destination"
}

fun NavGraphBuilder.contactGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = ContactNavigation.route
    ) {
        ContactScreen(
            onBackClick = onBackClick
        )
    }
}