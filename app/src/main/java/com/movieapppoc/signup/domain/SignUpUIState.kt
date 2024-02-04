package com.movieapppoc.signup.domain

data class SignUpUIState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    var signUpError: String = "",
    val fNameError : Boolean = false,
    val lNameError : Boolean = false,
    val emailError : Boolean = false,
    val passError : Boolean = false,
)
