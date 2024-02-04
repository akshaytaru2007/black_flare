package com.movieapppoc.signup.presentation

data class SignUpUIState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val fNameError : Boolean = false,
    val lNameError : Boolean = false,
    val emailError : Boolean = false,
    val passError : Boolean = false,
)
