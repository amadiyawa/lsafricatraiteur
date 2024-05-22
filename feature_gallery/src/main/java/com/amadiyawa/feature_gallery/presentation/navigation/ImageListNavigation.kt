package com.amadiyawa.feature_gallery.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_gallery.presentation.screen.imagelist.ImageListScreen

object ImageListNavigation: AppNavigationDestination {
    private const val IMAGE_LIST = "image_list"

    override val route: String = IMAGE_LIST

    override val destination: String = "image_list_destination"
}

fun NavGraphBuilder.imageListGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = ImageListNavigation.route
    ) {
        ImageListScreen(
            onBackClick = onBackClick
        )
    }
}