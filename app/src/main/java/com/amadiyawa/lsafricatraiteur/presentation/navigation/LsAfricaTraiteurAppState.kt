package com.amadiyawa.lsafricatraiteur.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationDestination
import com.amadiyawa.feature_base.presentation.navigation.AppState

class LsAfricaTraiteurAppState(
    private val windowSizeClass: WindowSizeClass,
    navController: NavHostController
): AppState(navController) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    private val shouldShowNavigation: Boolean
        @Composable get() {
            val route = currentDestination?.route
            val hierarchy = currentDestination?.hierarchy
            return topLevelDestinations.any {
                it.route == route ||
                        // In case of nested graph find in the hierarchy and check
                        // the destination (child linked with the bottom bar)
                        (hierarchy?.any { it1 -> it1.route == it.destination } ?: false)
            }
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact) && shouldShowNavigation

    val shouldShowNavRail: Boolean
        @Composable get() = !shouldShowBottomBar && shouldShowNavigation

    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination.Service,
        TopLevelDestination.Delivery,
        TopLevelDestination.Gallery,
        TopLevelDestination.Contact
    )

    fun navigateApp(destination: AppNavigationDestination, route: String? = null){
        trace("Navigation: $destination"){
            if(destination is TopLevelDestination){
                navController.navigate((route ?: destination.route)){
                    popUpTo(navController.graph.findStartDestination().id){ saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            else navController.navigate(route ?: destination.route)
        }
    }
}