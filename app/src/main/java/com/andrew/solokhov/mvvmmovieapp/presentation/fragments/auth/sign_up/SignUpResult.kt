package com.andrew.solokhov.mvvmmovieapp.presentation.fragments.auth.sign_up

sealed class SignUpResult {
    data object Empty: SignUpResult()
    data class Success(val result: Boolean?): SignUpResult()
    data class Error(val message: String?): SignUpResult()
    data object Loading: SignUpResult()
}