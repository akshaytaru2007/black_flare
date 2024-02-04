package com.movieapppoc.signup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.movieapppoc.R
import com.movieapppoc.core.presentation.common_components.AppPasswordTextField
import com.movieapppoc.core.presentation.common_components.AppTextField
import com.movieapppoc.core.presentation.common_components.ButtonComponent
import com.movieapppoc.core.presentation.common_components.CheckboxComponent
import com.movieapppoc.core.presentation.common_components.ClickableLoginTextComponent
import com.movieapppoc.core.presentation.common_components.HeadingTextComponent
import com.movieapppoc.core.presentation.common_components.NormalTextComponent

@Composable
fun SignUpScreen(
    navigateTnC: (() -> Unit)?,
    navigateToSignIn: (() -> Unit)?,
    signUpViewModel: SignUpViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {

        NormalTextComponent(value = stringResource(R.string.hello))
        HeadingTextComponent(value = stringResource(R.string.create_an_account))


        Spacer(modifier = Modifier.height(20.dp))

        AppTextField(
            labelValue = stringResource(R.string.first_name),
            imageVector = Icons.Filled.Face,
            isError = signUpViewModel.signUpUIState.value.fNameError,
            onTextChange = {
                signUpViewModel.onEvent(UIEvent.FirstNameChanged(it))
            }
        )
        AppTextField(
            labelValue = stringResource(R.string.last_name),
            imageVector = Icons.Filled.Face,
            isError = signUpViewModel.signUpUIState.value.lNameError,
            onTextChange = {
                signUpViewModel.onEvent(UIEvent.LastNameChanged(it))
            }
        )
        AppTextField(
            labelValue = stringResource(R.string.email),
            imageVector = Icons.Filled.Email,
            isError = signUpViewModel.signUpUIState.value.emailError,
            onTextChange = {
                signUpViewModel.onEvent(UIEvent.EmailChanged(it))
            }
        )
        AppPasswordTextField(
            labelValue = stringResource(R.string.password),
            imageVector = Icons.Filled.Password,
            isError = signUpViewModel.signUpUIState.value.passError,
        ) {
            signUpViewModel.onEvent(UIEvent.PasswordChanged(it))
        }

        CheckboxComponent(value = stringResource(R.string.terms_and_condition),
            onTextSelected = {
                navigateTnC?.invoke()
            })

        Spacer(modifier = Modifier.heightIn(50.dp))


        ButtonComponent(stringResource(R.string.sign_up)) {
            signUpViewModel.onEvent(UIEvent.RegisterButtonClicked)
        }

        Spacer(modifier = Modifier.heightIn(30.dp))

        DividerComponent()

        Spacer(modifier = Modifier.heightIn(20.dp))

        ClickableLoginTextComponent(
            initialText = "Already have an account ",
            clickableText = "Sign In"
        ) {
            navigateToSignIn?.invoke()
        }
    }
}

@Composable
fun DividerComponent() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = colorResource(id = android.R.color.darker_gray),
            thickness = 1.dp
        )

        Text(
            text = " OR ",
            fontSize = 18.sp,
            color = colorResource(id = R.color.colorText)
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = colorResource(id = android.R.color.darker_gray),
            thickness = 1.dp
        )
    }

}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(null, null)
}