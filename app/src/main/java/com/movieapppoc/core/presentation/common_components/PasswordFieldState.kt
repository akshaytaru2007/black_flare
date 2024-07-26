package com.movieapppoc.core.presentation.common_components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

class PasswordFieldState(inputText : String, isPasswordVisible : Boolean) {
    var text by mutableStateOf(inputText)
    var passwordVisibilty by mutableStateOf(isPasswordVisible)

    companion object {
        val Saver : Saver<PasswordFieldState, *> = listSaver(
            save = { listOf(it.text, it.passwordVisibilty) },
            restore = {
                PasswordFieldState(inputText = it[0].toString(), isPasswordVisible = it[1] as Boolean)
            }
        )
    }


}