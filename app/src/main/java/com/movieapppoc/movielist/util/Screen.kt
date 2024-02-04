package com.movieapppoc.movielist.util

sealed class Screen(val rout: String) {
    object Home : Screen("main")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    object Details : Screen("details")
    object Account : Screen("account")
    object SignIn : Screen("sign")
    object SignUp : Screen("signup")
    object TermsAndCondition : Screen("tnc")
}