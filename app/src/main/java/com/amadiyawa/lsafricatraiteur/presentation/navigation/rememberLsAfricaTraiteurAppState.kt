package com.amadiyawa.lsafricatraiteur.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberLsAfricaTraiteurAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): LsAfricaTraiteurAppState {
    return remember(windowSizeClass, navController){
        LsAfricaTraiteurAppState(windowSizeClass, navController)
    }
}