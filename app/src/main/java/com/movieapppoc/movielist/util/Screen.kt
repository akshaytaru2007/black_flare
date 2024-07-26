package com.movieapppoc.movielist.util

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    data object Details : Screen("details") {

        val movieIdArg = "movieId"
        val routeWithArgs = "$rout/{$movieIdArg}"
        val argument = listOf(
            navArgument(movieIdArg) { type = NavType.IntType }
        )
        val deepLink = listOf(navDeepLink {
            uriPattern = "movieapppoc://$rout/{$movieIdArg}}"
        })
    }
    object Account : Screen("account") {
        val deepLink = listOf(navDeepLink {
            uriPattern = "movieapppoc://$rout}}"
        })
    }
    object SignIn : Screen("sign")
    object SignUp : Screen("signup")
    object TermsAndCondition : Screen("tnc")
}