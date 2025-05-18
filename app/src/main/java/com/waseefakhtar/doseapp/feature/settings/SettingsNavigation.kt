package com.waseefakhtar.doseapp.feature.settings

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.waseefakhtar.doseapp.core.navigation.DoseNavigationDestination

object SettingsNavigation : DoseNavigationDestination {
    override val route = "settings_route"
    override val destination = "settings_destination"
}

fun NavGraphBuilder.settingsGraph(
    bottomBarVisibility: MutableState<Boolean>,
    fabVisibility: MutableState<Boolean>
) {
    composable(route = SettingsNavigation.route) {
        LaunchedEffect(null) {
            bottomBarVisibility.value = true
            fabVisibility.value = false
        }
        SettingsRoute()
    }
}