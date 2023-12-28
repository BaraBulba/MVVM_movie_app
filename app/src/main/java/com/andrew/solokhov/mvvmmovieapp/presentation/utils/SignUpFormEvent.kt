package com.andrew.solokhov.mvvmmovieapp.presentation.utils

sealed class SignUpFormEvent {
    data class EmailChanged(val email: String) : SignUpFormEvent()
    data class FullNameChanged(val fullName: String) : SignUpFormEvent()
    data class PasswordChanged(val password: String) : SignUpFormEvent()
    data class AcceptTermsAndPolicy(val isAccepted: Boolean) : SignUpFormEvent()
    data object SignUp: SignUpFormEvent()
}