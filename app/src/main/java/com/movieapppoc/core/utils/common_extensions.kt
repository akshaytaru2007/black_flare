package com.movieapppoc.core.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


fun NavHostController.navigateSingleTopTo(route : String) {
    navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true

    }
}