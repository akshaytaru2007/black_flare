package com.movieapppoc.signup.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.movieapppoc.signup.domain.Validator

class SignUpViewModel : ViewModel() {

    val signUpUIState = mutableStateOf(SignUpUIState())


    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.FirstNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    firstName = event.firstName,
                    fNameError = Validator.validateFirstName(
                        event.firstName
                    )
                )
            }

            is UIEvent.EmailChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    email = event.email,
                    emailError = Validator.validateEmailName(
                        event.email
                    )
                )
            }

            is UIEvent.LastNameChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    lastName = event.lastName,
                    lNameError = Validator.validateLastName(
                        event.lastName
                    )
                )
            }

            is UIEvent.PasswordChanged -> {
                signUpUIState.value = signUpUIState.value.copy(
                    password = event.password,
                    passError = Validator.validatePassword(
                        event.password
                    )
                )
            }

            is UIEvent.RegisterButtonClicked -> {
                signUpAction()
            }
        }
    }

    private fun signUpAction() {
        Log.d(SignUpViewModel::class.java.name, " OnSignUpClick $signUpUIState")
        validateAndProcess()
    }

    private fun validateAndProcess() {
        val fNameResult = Validator.validateFirstName(
            signUpUIState.value.firstName
        )
        val lNameResult = Validator.validateLastName(
            signUpUIState.value.lastName
        )
        val emailResult = Validator.validateEmailName(
            signUpUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            signUpUIState.value.password
        )

        if(fNameResult && lNameResult && emailResult && passwordResult) {
            // process sign operation
            println("xxx show error")
        } else {
            println("xxx process sign in operation")
        }
    }
}