package com.amadiyawa.feature_base.presentation.navigation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun RowScope.AppNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable () -> Unit = {  },
    alwaysShowLabel: Boolean = true
){
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if(selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MeshNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MeshNavigationDefaults.navigationContentColor(),
            selectedTextColor = MeshNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = MeshNavigationDefaults.navigationContentColor(),
            indicatorColor = MeshNavigationDefaults.navigationIndicatorColor()
        )
    )
}

@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
){
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MeshNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

@Composable
fun AppNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
){
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = MeshNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MeshNavigationDefaults.navigationContentColor(),
            selectedTextColor = MeshNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = MeshNavigationDefaults.navigationContentColor(),
            indicatorColor = MeshNavigationDefaults.navigationIndicatorColor()
        )
    )
}

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
){
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        header = header,
        content = content
    )
}

object MeshNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurface
    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.primary
    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.secondary
}