package com.amadiyawa.lsafricatraiteur.presentation.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationBar
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationBarItem
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationRail
import com.amadiyawa.feature_base.presentation.navigation.AppNavigationRailItem
import com.amadiyawa.feature_base.presentation.theme.AppTheme
import com.amadiyawa.lsafricatraiteur.presentation.navigation.AppNavHost
import com.amadiyawa.lsafricatraiteur.presentation.navigation.LsAfricaTraiteurAppState
import com.amadiyawa.lsafricatraiteur.presentation.navigation.TopLevelDestination
import com.amadiyawa.lsafricatraiteur.presentation.navigation.rememberLsAfricaTraiteurAppState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LsAfricaTraiteurApp(
    windowSizeClass: WindowSizeClass,
    appState: LsAfricaTraiteurAppState = rememberLsAfricaTraiteurAppState(windowSizeClass = windowSizeClass)
) {
    AppTheme {
        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    LsAfricaTraiteurBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigate,
                        currentDestination = appState.currentDestination
                    )
                }
            }
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (appState.shouldShowNavRail) {
                    LsAfricaTraiteurNavRail(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigate,
                        currentDestination = appState.currentDestination
                    )
                }
                AppNavHost(
                    navController = appState.navController,
                    onNavigateToDestination = appState::navigate,
                    onBackClick = appState::onBackClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun LsAfricaTraiteurNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
){
    AppNavigationRail(modifier = modifier) {
        destinations.forEach{ destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            AppNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if(selected) destination.selectedIcon else destination.unselectedIcon
                    Icon(imageVector = icon, contentDescription = null)
                },
                label = { Text(text = stringResource(id = destination.title))}
            )
        }
    }
}

@Composable
fun LsAfricaTraiteurBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
){
    Surface(color = MaterialTheme.colorScheme.surface) {
        AppNavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
        ) {
            destinations.forEach{ destination ->
                val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                AppNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination)},
                    icon = {
                        val icon = if(selected) destination.selectedIcon else destination.unselectedIcon
                        Icon(imageVector = icon, contentDescription = null)
                    },
                    label = {
                        Text(
                            text = stringResource(id = destination.title),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                )
            }
        }
    }
}