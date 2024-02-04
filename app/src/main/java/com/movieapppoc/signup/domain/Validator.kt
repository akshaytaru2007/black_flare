package com.movieapppoc.signup.domain

object Validator {

    fun validateFirstName(fName: String): Boolean {
        return fName.isNotEmpty() && fName.length <= 3
    }

    fun validateLastName(lName: String): Boolean {
        return lName.isNotEmpty() && lName.length <= 3
    }

    fun validateEmailName(email: String): Boolean {
        return email.isNotEmpty() && email.length <= 3
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length <= 4
    }
}
