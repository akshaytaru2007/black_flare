package com.movieapppoc.signup.domain

sealed class UIEvent {

    data class FirstNameChanged(val firstName: String) : UIEvent()
    data class LastNameChanged(val lastName: String) : UIEvent()
    data class EmailChanged(val email: String) : UIEvent()
    data class PasswordChanged(val password: String) : UIEvent()

    data object RegisterButtonClicked : UIEvent()
}

sealed class SignUpEvent() {
    data object Success : SignUpEvent()
}

